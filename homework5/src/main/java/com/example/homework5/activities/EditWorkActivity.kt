package com.example.homework5.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.homework5.R
import com.example.homework5.data.WorkData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.database.CarsDatabase
import com.example.homework5.database.WorksDatabaseDAO

class EditWorkActivity : AppCompatActivity() {

    private lateinit var dao: WorksDatabaseDAO
    private var workObject: WorkData? = null
    private var workId: Long = 0
    private var color: Int? = null
    private var progress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_work)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // инициализация БД
        dao = CarsDatabase.init(this).getWorkDatabaseDAO()

        getIntentData(intent)

        val time: TextView = findViewById(R.id.setTime)
        val workNameEditText: EditText = findViewById(R.id.workNameEditText)
        val workDescriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val workCoastEditText: EditText = findViewById(R.id.coastEditText)
        val pending: ImageView = findViewById(R.id.pending)
        val inProgress: ImageView = findViewById(R.id.inProgress)
        val completed: ImageView = findViewById(R.id.completed)
        val submit: ImageView = findViewById(R.id.submitButton)
        val backButton: ImageView = findViewById(R.id.backButton)

        findViewById<TextView>(R.id.totleInToolbar).text = workObject?.workName
                ?: getString(R.string.edit)

        fillPage(time,
                workNameEditText,
                workDescriptionEditText,
                workCoastEditText,
                pending, inProgress,
                completed)

        pending.setOnClickListener {
            pendingSetColor(pending, inProgress, completed)
        }

        inProgress.setOnClickListener {
            inProgressSetColor(pending, inProgress, completed)
        }

        completed.setOnClickListener {
            completedSetColor(pending, inProgress, completed)
        }

        // нажата кнопка НАЗАД
        backButton.setOnClickListener { finish() }

        // Нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (color == null) {
                Toast.makeText(this, getString(R.string.selectProgress), Toast.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty()
                    && workDescriptionEditText.text.isNotEmpty()
                    && workCoastEditText.text.isNotEmpty()) {

                saveDataAndCloseActivity(
                        createObject(workNameEditText,
                                time,
                                workDescriptionEditText,
                                workCoastEditText))
            } else {
                Toast.makeText(this, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveDataAndCloseActivity(work: WorkData) {
        dao.update(work)
        finish()
    }

    private fun fillPage(time: TextView, workNameEditText: EditText, workDescriptionEditText: EditText, workCoastEditText: EditText, pending: ImageView, inProgress: ImageView, completed: ImageView) {
        if (workObject != null) {
            time.text = workObject?.time
            workNameEditText.setText(workObject?.workName)
            workDescriptionEditText.setText(workObject?.workDescription)
            workCoastEditText.setText(workObject?.coast)

            when (progress) {
                getString(Constants.PROGRESS_PENDING) -> pending
                        .setColorFilter(resources.getColor(Constants.PENDING, theme))
                getString(Constants.PROGRESS_IN_PROGRESS) -> inProgress
                        .setColorFilter(resources.getColor(Constants.IN_PROGRESS, theme))
                getString(Constants.PROGRESS_COMPLETE) -> completed
                        .setColorFilter(resources.getColor(Constants.COMPLETE, theme))
            }

        }
    }

    private fun getIntentData(intent: Intent) {
        workId = intent.getLongExtra(Constants.POSITION_CAR_IN_DB, 0)
        workObject = dao.getWork(workId)

        progress = workObject?.progress ?: getString(Constants.PROGRESS_PENDING)
        color = workObject?.color
    }

    private fun completedSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        inProgress.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        completed.setColorFilter(resources.getColor(Constants.COMPLETE, theme))
        color = Constants.COMPLETE
        progress = getString(Constants.PROGRESS_COMPLETE)
    }

    private fun inProgressSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        inProgress.setColorFilter(resources.getColor(Constants.IN_PROGRESS, theme))
        completed.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        color = Constants.IN_PROGRESS
        progress = getString(Constants.PROGRESS_IN_PROGRESS)
    }

    private fun pendingSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(resources.getColor(Constants.PENDING, theme))
        inProgress.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        completed.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        color = Constants.PENDING
        progress = getString(Constants.PROGRESS_PENDING)
    }

    private fun createObject(workNameEditText: EditText,
                             time: TextView,
                             workDescriptionEditText: EditText,
                             coastEditText: EditText): WorkData {

        return WorkData(workNameEditText.text.toString(),
                workDescriptionEditText.text.toString(),
                time.text.toString(),
                progress!!, // проверяется color на null, если color не null, то и тут будет не null
                coastEditText.text.toString(),
                color!!).also {
            it.parentCar = workObject?.parentCar
            it.id = workObject?.id
        }
    }

}