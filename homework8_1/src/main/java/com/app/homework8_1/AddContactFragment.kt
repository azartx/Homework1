package com.app.homework8_1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

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

        backButton.setOnClickListener {
            /*setResult(Activity.RESULT_CANCELED, intent)
            finish()*/
        }

        radioGroup.setOnCheckedChangeListener { _, button ->
            when(button) {
                R.id.numberRadioButton -> numberOrEmailEditText.setHint(R.string.number)
                R.id.emailRadioButton -> numberOrEmailEditText.setHint(R.string.email)
            }
        }

        submitButton.setOnClickListener {

            var image = 0
            val name = nameEditText.text.toString()
            val numberOrEmail = numberOrEmailEditText.text.toString()

            var check = true

            if (numberRadioButton.isChecked) {
                image = R.drawable.ic_baseline_contact_phone_24
            } else if (emailRadioButton.isChecked) {
                image = R.drawable.ic_baseline_email_24
            } else {
                Toast.makeText(view.context, "Set phone or email", Toast.LENGTH_LONG).show()
                check = false
            }

            if (name.length < 1 || numberOrEmail.length < 1) {
                Toast.makeText(view.context, "Заполните все поля", Toast.LENGTH_LONG).show()
                check = false
            }

            //finishActivity(intent, image, name, numberOrEmail, check)

        }


    }



}