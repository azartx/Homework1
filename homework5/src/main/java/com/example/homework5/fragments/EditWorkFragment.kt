package com.example.homework5.fragments

import android.app.AlertDialog
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
import com.example.homework5.data.staticData.Constants.Companion.WORK_RECYCLE_FRAGMENT
import com.example.homework5.database.DatabaseRepository
import kotlinx.coroutines.launch

class EditWorkFragment : Fragment(R.layout.fragment_edit_work) {

    private lateinit var databaseRepository: DatabaseRepository
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        getIntentData()

        time = view.findViewById(R.id.setTime)
        workNameEditText = view.findViewById(R.id.workNameEditText)
        workDescriptionEditText = view.findViewById(R.id.descriptionEditText)
        workCoastEditText = view.findViewById(R.id.coastEditText)
        pending = view.findViewById(R.id.pending)
        inProgress = view.findViewById(R.id.inProgress)
        completed = view.findViewById(R.id.completed)
        submit = view.findViewById(R.id.submitButton)
        backButton = view.findViewById(R.id.backButton)
        removeButton = view.findViewById(R.id.removeButton)

        view.findViewById<TextView>(R.id.totleInToolbar).text = workObject?.workName
                ?: getString(R.string.edit)

        pending.setOnClickListener { pendingSetColor() }

        inProgress.setOnClickListener { inProgressSetColor() }

        completed.setOnClickListener { completedSetColor() }

        // нажата кнопка НАЗАД
        backButton.setOnClickListener { this.activity?.onBackPressed() }

        // нажата кнопка УДАЛИТЬ
        removeButton.setOnClickListener { showRemoveDialog() }

        // Нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (color == null) {
                Toast.makeText(view.context, getString(R.string.selectProgress), Toast.LENGTH_LONG).show()
            } else if (workNameEditText.text.isNotEmpty()
                    && workDescriptionEditText.text.isNotEmpty()
                    && workCoastEditText.text.isNotEmpty()) {

                saveDataAndCloseActivity(
                        createObject(workNameEditText,
                                time,
                                workDescriptionEditText,
                                workCoastEditText))
            } else {
                Toast.makeText(view.context, getString(R.string.fillAllFields), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveDataAndCloseActivity(work: WorkData) {
        databaseRepository.updateWork(work)
        (activity as ChangeFragmentListener).onChangeFragment(WORK_RECYCLE_FRAGMENT, null)
    }

    private fun fillPage() {
        if (workObject != null) {
            time.text = workObject?.time
            workNameEditText.setText(workObject?.workName)
            workDescriptionEditText.setText(workObject?.workDescription)
            workCoastEditText.setText(workObject?.coast)

            when (progress) {
                getString(Constants.PROGRESS_PENDING) -> pending
                        .setColorFilter(resources.getColor(Constants.PENDING, context?.theme))
                getString(Constants.PROGRESS_IN_PROGRESS) -> inProgress
                        .setColorFilter(resources.getColor(Constants.IN_PROGRESS, context?.theme))
                getString(Constants.PROGRESS_COMPLETE) -> completed
                        .setColorFilter(resources.getColor(Constants.COMPLETE, context?.theme))
            }

        }
    }

    private fun getIntentData() {
        workId = arguments?.getLong(Constants.POSITION_CAR_IN_DB, 0) ?: 0
        databaseRepository.mainScope().launch {
            workObject = databaseRepository.getWork(workId)
            fillPage()
            progress = workObject?.progress ?: getString(Constants.PROGRESS_PENDING)
            color = workObject?.color
        }
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
                color!!).also {
            it.parentCar = workObject?.parentCar
            it.id = workObject?.id
        }
    }

    private fun showRemoveDialog() {
        AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.remove_work))
            setMessage(getString(R.string.alertRemoveWorkMessage))
            setPositiveButton(getString(R.string.yesButton)) { _, _ ->
                workObject?.let { databaseRepository.deleteWork(it) }
                (activity as ChangeFragmentListener).onChangeFragment(WORK_RECYCLE_FRAGMENT, null)
            }
            setNegativeButton(getString(R.string.cancleButton)) { dialogInterface, _ -> dialogInterface.cancel() }
            setCancelable(false)
            create()
            show()
        }
    }

}