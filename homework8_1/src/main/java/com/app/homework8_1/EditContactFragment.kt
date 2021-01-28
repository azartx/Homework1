package com.app.homework8_1

import android.app.Instrumentation
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.app.homework8_1.Constants.Companion.RECYCLER_LIST_FRAGMENT

class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    private lateinit var actualData: ContactBody
    private var position: Int? = null

    private lateinit var backButton: ImageButton
    private lateinit var submitButton: ImageButton
    private lateinit var removeButton: Button
    private lateinit var name: EditText
    private lateinit var emailOfPhone: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)
        removeButton = view.findViewById(R.id.removeContactButton)
        name = view.findViewById(R.id.nameEditEditText)
        emailOfPhone = view.findViewById(R.id.numberOrEmailEditEditText)

        getData()
        fillPage()

        backButton.setOnClickListener {
            this.activity?.onBackPressed()
        }

    }

    private fun getData() {
        actualData = arguments?.getSerializable("edit pool") as ContactBody
        position = arguments?.getInt("position")
        Log.i("FFFF", position.toString())
    }

    private fun fillPage() {
        name.setText(actualData.contactName)
        emailOfPhone.setText(actualData.emailOrNumber)
    }

}