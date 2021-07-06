package com.example.homework5.activities

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
import com.example.homework5.data.staticData.Constants.Companion.PARENT_CAR
import com.example.homework5.database.DatabaseRepository
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class AddWorkActivity : AppCompatActivity() {

    private var color: Int? = null
    private var progress: String? = null

    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var submit: ImageView
    private lateinit var time: TextView
    private lateinit var workNameEditText: EditText
    private lateinit var workDescriptionEditText: EditText
    private lateinit var coastEditText: EditText
    private lateinit var pending: ImageView
    private lateinit var inProgress: ImageView
    private lateinit var completed: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // инициализация БД
        databaseRepository = DatabaseRepository(applicationContext)

        submit = findViewById(R.id.submitButton)
        time = findViewById(R.id.setTime)
        workNameEditText = findViewById(R.id.workNameEditText)
        workDescriptionEditText = findViewById(R.id.descriptionEditText)
        coastEditText = findViewById(R.id.coastEditText)
        pending = findViewById(R.id.pending)
        inProgress = findViewById(R.id.inProgress)
        completed = findViewById(R.id.completed)

        time.text = getCurrentData()

        pending.setOnClickListener { pendingSetColor() }

        inProgress.setOnClickListener { inProgressSetColor() }

        completed.setOnClickListener { completedSetColor() }

        // Нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (color == null) {
                Toast.makeText(this, getString(R.string.selectProgress), Toast.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty()
                    && workDescriptionEditText.text.isNotEmpty()
                    && coastEditText.text.isNotEmpty()) {

                createObject(workNameEditText, time, workDescriptionEditText, coastEditText).apply {
                    parentCar = intent.getStringExtra(PARENT_CAR)
                    databaseRepository.addWorkToDatabase(this)
                    finish()
                }
            } else {
                Toast.makeText(this, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getCurrentData(): String {
        return SimpleDateFormat(
                "dd/M/yyyy hh:mm:ss",
                Locale.getDefault())
                .format(Date())
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
                color!!)
    }

}