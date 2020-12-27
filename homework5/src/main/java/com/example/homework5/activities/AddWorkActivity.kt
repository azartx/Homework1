package com.example.homework5.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class AddWorkActivity : AppCompatActivity() {

    private var carObject: CarData? = null
    private var carPosition: Int = 0
    private var color: String? = null
    private var progress: String? = null
    private var currentData: String = SimpleDateFormat(
            "dd/M/yyyy hh:mm:ss",
            Locale.getDefault())
            .format(Date())

    companion object {
        const val OBJECT = "editCar"
        const val POSITION = "editPosition"

        const val PENDING = "#FF0000"
        const val IN_PROGRESS = "#FF8C00"
        const val COMPLETE = "#00FF00"
        const val DEFAULT_COLOR = "#616161"

        const val PROGRESS_PENDING = "pending"
        const val PROGRESS_IN_PROGRESS = "in progress"
        const val PROGRESS_COMPLETE = "complete"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent
        getIntentData(intent)

        val submit: ImageView = findViewById(R.id.submitButton)
        val time: TextView = findViewById(R.id.setTime)
        val workNameEditText: EditText = findViewById(R.id.workNameEditText)
        val workDescriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val coastEditText: EditText = findViewById(R.id.coastEditText)
        val pending: ImageView = findViewById(R.id.pending)
        val inProgress: ImageView = findViewById(R.id.inProgress)
        val completed: ImageView = findViewById(R.id.completed)

        time.text = currentData

        pending.setOnClickListener {
            pendingSetColor(pending, inProgress, completed)
        }

        inProgress.setOnClickListener {
            inProgressSetColor(pending, inProgress, completed)
        }

        completed.setOnClickListener {
            completedSetColor(pending, inProgress, completed)
        }

        // Нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (color == null) {
                Toast.makeText(this, getString(R.string.selectProgress), Toast.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty()
                    && workDescriptionEditText.text.isNotEmpty()
                    && coastEditText.text.isNotEmpty()) {

                val work = createObject(workNameEditText, time, workDescriptionEditText, coastEditText)

                intent.putExtra(OBJECT, work)
                setResult(RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun completedSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(Color.parseColor(DEFAULT_COLOR))
        inProgress.setColorFilter(Color.parseColor(DEFAULT_COLOR))
        completed.setColorFilter(Color.parseColor(COMPLETE))
        color = COMPLETE
        progress = PROGRESS_COMPLETE
    }

    private fun inProgressSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(Color.parseColor(DEFAULT_COLOR))
        inProgress.setColorFilter(Color.parseColor(IN_PROGRESS))
        completed.setColorFilter(Color.parseColor(DEFAULT_COLOR))
        color = IN_PROGRESS
        progress = PROGRESS_IN_PROGRESS
    }

    private fun pendingSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(Color.parseColor(PENDING))
        inProgress.setColorFilter(Color.parseColor(DEFAULT_COLOR))
        completed.setColorFilter(Color.parseColor(DEFAULT_COLOR))
        color = PENDING
        progress = PROGRESS_PENDING
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
                color.toString(),
                carPosition)
    }

    private fun getIntentData(intent: Intent) {
        carObject = intent.getParcelableExtra(OBJECT)
        carPosition = intent.getIntExtra(POSITION, 0)
    }

}