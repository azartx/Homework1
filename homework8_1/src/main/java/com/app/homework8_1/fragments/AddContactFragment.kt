package com.app.homework8_1.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.app.homework8_1.ContactBody
import com.app.homework8_1.R
import com.app.homework8_1.db.ContactsDAO
import com.app.homework8_1.db.ContactsDB
import com.app.homework8_1.db.SingletonDatabase

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    private lateinit var numberRadioButton: RadioButton
    private lateinit var emailRadioButton: RadioButton
    private lateinit var nameEditText: EditText
    private lateinit var numberOrEmailEditText: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var backButton: ImageButton
    private lateinit var submitButton: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberRadioButton = view.findViewById(R.id.numberRadioButton)
        numberOrEmailEditText = view.findViewById(R.id.numberOrEmailEditText)
        nameEditText = view.findViewById(R.id.nameEditText)
        emailRadioButton = view.findViewById(R.id.emailRadioButton)
        radioGroup = view.findViewById(R.id.radioGroup)
        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)

        backButton.setOnClickListener { this.activity?.onBackPressed() }

        submitButton.setOnClickListener { submitIsPressed() }

        radioGroup.setOnCheckedChangeListener { _, button ->
            when (button) {
                R.id.numberRadioButton -> numberOrEmailEditText.setHint(R.string.number)
                R.id.emailRadioButton -> numberOrEmailEditText.setHint(R.string.email)
            }
        }

    }

    private fun submitIsPressed() {
        var image = 0
        val name = nameEditText.text.toString()
        val numberOrEmail = numberOrEmailEditText.text.toString()

        when {
            numberRadioButton.isChecked -> {
                image = R.drawable.ic_baseline_contact_phone_24
            }
            emailRadioButton.isChecked -> {
                image = R.drawable.ic_baseline_email_24
            }
        }

        if (name.isEmpty() || numberOrEmail.isEmpty()) {
            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
        } else {
            SingletonDatabase.contactDB.addContact(ContactBody(image, name, numberOrEmail))
            parentFragmentManager.beginTransaction()
                    .replace(R.id.rootFragments, RecyclerListFragment::class.java, null)
                    .commit()
        }
    }
}