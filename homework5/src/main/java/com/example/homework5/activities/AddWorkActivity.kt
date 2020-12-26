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

class AddWorkActivity : AppCompatActivity() {

    private var carObject: CarData? = null
    private var carPosition: Int = 0
    private var color: String? = null

    companion object {
        const val OBJECT = "editCar"
        const val POSITION = "editPosition"
        const val PENDING = "#FF0000"
        const val IN_PROGRESS = "#00FF00"
        const val COMPLETE = "#FF8C00"
        const val DEFAULT_COLOR = "#616161"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val submit: ImageView = findViewById(R.id.submitButton)
        val time: TextView = findViewById(R.id.setTime)
        val workNameEditText: EditText = findViewById(R.id.workNameEditText)
        val workDescriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val coastEditText: EditText = findViewById(R.id.coastEditText)
        val pending: ImageView = findViewById(R.id.pending)
        val inProgress: ImageView = findViewById(R.id.inProgress)
        val completed: ImageView = findViewById(R.id.completed)
        /*val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { radioGroup1: RadioGroup, i: Int ->

        }*/


        pending.setOnClickListener {
            pending.setColorFilter(Color.parseColor(PENDING))
            inProgress.setColorFilter(Color.parseColor(DEFAULT_COLOR))
            completed.setColorFilter(Color.parseColor(DEFAULT_COLOR))
            color = PENDING
        }

        inProgress.setOnClickListener {
            pending.setColorFilter(Color.parseColor(DEFAULT_COLOR))
            inProgress.setColorFilter(Color.parseColor(IN_PROGRESS))
            completed.setColorFilter(Color.parseColor(DEFAULT_COLOR))
            color = IN_PROGRESS
        }

        completed.setOnClickListener {
            pending.setColorFilter(Color.parseColor(DEFAULT_COLOR))
            inProgress.setColorFilter(Color.parseColor(DEFAULT_COLOR))
            completed.setColorFilter(Color.parseColor(COMPLETE))
            color = COMPLETE
        }

        val intent = intent

        getIntentData(intent)

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

    private fun createObject(workNameEditText: EditText, time: TextView, workDescriptionEditText: EditText, coastEditText: EditText): WorkData {
        return WorkData(workNameEditText.text.toString(),
                time.text.toString(),
                workDescriptionEditText.text.toString(),
                coastEditText.text.toString(),
                color.toString(),
                carPosition)
    }

    private fun getIntentData(intent: Intent) {
        carObject = intent.getParcelableExtra(OBJECT)
        carPosition = intent.getIntExtra(POSITION, 0)
    }

}