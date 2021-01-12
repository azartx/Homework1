package com.example.homework5.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var time: TextView
    private lateinit var workNameEditText: EditText
    private lateinit var workDescriptionEditText: EditText
    private lateinit var workCoastEditText: EditText
    private lateinit var pending: ImageView
    private lateinit var inProgress: ImageView
    private lateinit var completed: ImageView
    private lateinit var submit: ImageView
    private lateinit var backButton: ImageView
    private lateinit var removeButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_work)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // инициализация БД
        dao = CarsDatabase.init(this).getWorkDatabaseDAO()

        getIntentData(intent)

        time = findViewById(R.id.setTime)
        workNameEditText = findViewById(R.id.workNameEditText)
        workDescriptionEditText = findViewById(R.id.descriptionEditText)
        workCoastEditText = findViewById(R.id.coastEditText)
        pending = findViewById(R.id.pending)
        inProgress = findViewById(R.id.inProgress)
        completed = findViewById(R.id.completed)
        submit = findViewById(R.id.submitButton)
        backButton = findViewById(R.id.backButton)
        removeButton = findViewById(R.id.removeButton)

        findViewById<TextView>(R.id.totleInToolbar).text = workObject?.workName
                ?: getString(R.string.edit)

        fillPage()

        pending.setOnClickListener { pendingSetColor() }

        inProgress.setOnClickListener { inProgressSetColor() }

        completed.setOnClickListener { completedSetColor() }

        // нажата кнопка НАЗАД
        backButton.setOnClickListener { finish() }

        // нажата кнопка УДАЛИТЬ
        removeButton.setOnClickListener { showRemoveDialog() }

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

    private fun fillPage() {
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

    private fun completedSetColor() {
        pending.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        inProgress.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        completed.setColorFilter(resources.getColor(Constants.COMPLETE, theme))
        color = Constants.COMPLETE
        progress = getString(Constants.PROGRESS_COMPLETE)
    }

    private fun inProgressSetColor() {
        pending.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        inProgress.setColorFilter(resources.getColor(Constants.IN_PROGRESS, theme))
        completed.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, theme))
        color = Constants.IN_PROGRESS
        progress = getString(Constants.PROGRESS_IN_PROGRESS)
    }

    private fun pendingSetColor() {
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

    private fun showRemoveDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.remove_work))
            setMessage(getString(R.string.alertRemoveWorkMessage))
            setPositiveButton(getString(R.string.yesButton)) { _, _ ->
                workObject?.let { dao.delete(it) }
                setResult(RESULT_OK, Intent())
                finish()
            }
            setNegativeButton(getString(R.string.cancleButton)) { dialogInterface, _ -> dialogInterface.cancel() }
            setCancelable(false)
            create()
            show()
        }
    }

}