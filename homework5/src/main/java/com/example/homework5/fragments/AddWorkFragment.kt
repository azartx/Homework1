package com.example.homework5.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework5.R
import com.example.homework5.data.WorkData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.database.DatabaseRepository
import java.text.SimpleDateFormat
import java.util.*

class AddWorkFragment : Fragment(R.layout.fragment_add_work) {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        submit = view.findViewById(R.id.submitButton)
        time = view.findViewById(R.id.setTime)
        workNameEditText = view.findViewById(R.id.workNameEditText)
        workDescriptionEditText = view.findViewById(R.id.descriptionEditText)
        coastEditText = view.findViewById(R.id.coastEditText)
        pending = view.findViewById(R.id.pending)
        inProgress = view.findViewById(R.id.inProgress)
        completed = view.findViewById(R.id.completed)

        time.text = getCurrentData()

        pending.setOnClickListener { pendingSetColor() }

        inProgress.setOnClickListener { inProgressSetColor() }

        completed.setOnClickListener { completedSetColor() }

        // Нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (color == null) {
                Toast.makeText(view.context, getString(R.string.selectProgress), Toast.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty()
                    && workDescriptionEditText.text.isNotEmpty()
                    && coastEditText.text.isNotEmpty()) {

                createObject(workNameEditText, time, workDescriptionEditText, coastEditText).apply {
                    parentCar = arguments?.getString(Constants.PARENT_CAR)
                    databaseRepository.addWorkToDatabase(this)
                    this@AddWorkFragment.activity?.onBackPressed()
                }
            } else {
                Toast.makeText(view.context, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
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
        pending.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, context?.theme))
        inProgress.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, context?.theme))
        completed.setColorFilter(resources.getColor(Constants.COMPLETE, context?.theme))
        color = Constants.COMPLETE
        progress = getString(Constants.PROGRESS_COMPLETE)
    }

    private fun inProgressSetColor() {
        pending.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, context?.theme))
        inProgress.setColorFilter(resources.getColor(Constants.IN_PROGRESS, context?.theme))
        completed.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, context?.theme))
        color = Constants.IN_PROGRESS
        progress = getString(Constants.PROGRESS_IN_PROGRESS)
    }

    private fun pendingSetColor() {
        pending.setColorFilter(resources.getColor(Constants.PENDING, context?.theme))
        inProgress.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, context?.theme))
        completed.setColorFilter(resources.getColor(Constants.DEFAULT_COLOR, context?.theme))
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