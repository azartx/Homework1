package com.example.homework5.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.homework5.R
import com.example.homework5.data.WorkData

class EditWorkActivity : AppCompatActivity() {

    private var workObject: WorkData? = null
    private var workPosition: Int = 0
    private var color: String? = null
    private var progress: String? = null

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
        setContentView(R.layout.activity_edit_work)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent

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

                val work = createObject(workNameEditText, time, workDescriptionEditText, workCoastEditText)

                intent.putExtra(OBJECT, work)
                intent.putExtra(POSITION, workPosition)
                setResult(RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun fillPage(time: TextView, workNameEditText: EditText, workDescriptionEditText: EditText, workCoastEditText: EditText, pending: ImageView, inProgress: ImageView, completed: ImageView) {
        if (workObject != null) {
            time.text = workObject?.time
            workNameEditText.setText(workObject?.workName)
            workDescriptionEditText.setText(workObject?.workDescription)
            workCoastEditText.setText(workObject?.coast)

            when (progress) {
                PROGRESS_PENDING -> pending.setColorFilter(Color.parseColor(PENDING))
                PROGRESS_IN_PROGRESS -> inProgress.setColorFilter(Color.parseColor(IN_PROGRESS))
                PROGRESS_COMPLETE -> completed.setColorFilter(Color.parseColor(COMPLETE))
            }

        }
    }

    private fun getIntentData(intent: Intent) {
        workObject = intent.getParcelableExtra(OBJECT)
        workPosition = intent.getIntExtra(POSITION, 0)

        progress = workObject?.progress ?: PROGRESS_PENDING
        color = workObject?.color
    }

    private fun completedSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(Color.parseColor(AddWorkActivity.DEFAULT_COLOR))
        inProgress.setColorFilter(Color.parseColor(AddWorkActivity.DEFAULT_COLOR))
        completed.setColorFilter(Color.parseColor(AddWorkActivity.COMPLETE))
        color = AddWorkActivity.COMPLETE
        progress = AddWorkActivity.PROGRESS_COMPLETE
    }

    private fun inProgressSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(Color.parseColor(AddWorkActivity.DEFAULT_COLOR))
        inProgress.setColorFilter(Color.parseColor(AddWorkActivity.IN_PROGRESS))
        completed.setColorFilter(Color.parseColor(AddWorkActivity.DEFAULT_COLOR))
        color = AddWorkActivity.IN_PROGRESS
        progress = AddWorkActivity.PROGRESS_IN_PROGRESS
    }

    private fun pendingSetColor(pending: ImageView, inProgress: ImageView, completed: ImageView) {
        pending.setColorFilter(Color.parseColor(AddWorkActivity.PENDING))
        inProgress.setColorFilter(Color.parseColor(AddWorkActivity.DEFAULT_COLOR))
        completed.setColorFilter(Color.parseColor(AddWorkActivity.DEFAULT_COLOR))
        color = AddWorkActivity.PENDING
        progress = AddWorkActivity.PROGRESS_PENDING
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
                workObject!!.positionInCarList)
    }

}