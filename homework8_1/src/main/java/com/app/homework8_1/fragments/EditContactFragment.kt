package com.app.homework8_1.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.homework8_1.ContactBody
import com.app.homework8_1.R
import com.app.homework8_1.db.SingletonDatabase.Companion.contactDB
import kotlinx.coroutines.launch

class EditContactFragment : Fragment(R.layout.fragment_edit_contact) {

    private lateinit var actualData: ContactBody
    private var contactId: Long = 0

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

        backButton.setOnClickListener { this.activity?.onBackPressed() }
        submitButton.setOnClickListener { submitIsPressed() }

        removeButton.setOnClickListener { contactDB.removeContact(contact = actualData).also { this.activity?.onBackPressed() } }

        contactDB.mainScope().launch {
            getData()
            fillPage()
        }

    }

    private suspend fun getData() {
        contactId = arguments?.getLong("contactId") ?: 0
        actualData = contactDB.getContact(contactId)
    }

    private fun fillPage() {
        name.setText(actualData.contactName)
        emailOfPhone.setText(actualData.emailOrNumber)
    }

    private fun submitIsPressed() {
        val name = name.text.toString()
        val numberOrEmail = emailOfPhone.text.toString()

        if (name.isEmpty() || numberOrEmail.isEmpty()) {
            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
        } else {
            contactDB.updateContact(fillContact(name, numberOrEmail))
            parentFragmentManager.beginTransaction()
                    .replace(R.id.rootFragments, RecyclerListFragment::class.java, null)
                    .commit()
        }
    }

    private fun fillContact(name: String, numberOrEmail: String) =
            ContactBody(actualData.image, name, numberOrEmail).also { it.id = contactId }

}