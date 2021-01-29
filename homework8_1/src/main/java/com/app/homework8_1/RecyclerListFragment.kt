package com.app.homework8_1

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.homework8_1.Constants.Companion.ADD_CONTACT_FRAGMENT
import com.app.homework8_1.Constants.Companion.EDIT_CONTACT_FRAGMENT
import java.util.*

class RecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noContactsTextView: TextView
    private lateinit var onContactClickListener: DataAdapter.OnContactClickListener
    private lateinit var addContactButton: ImageButton
    private lateinit var dataAdapter: DataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        noContactsTextView = view.findViewById(R.id.noContactsTextView)
        addContactButton = view.findViewById(R.id.addContactButton)

        onContactClickListener = DataAdapter.OnContactClickListener { contactBody: ContactBody?, position: Int ->
            Bundle().apply {
                putSerializable("edit pool", contactBody)
                putInt("position", position)
                (activity as ChangeFragmentListener).onChangeFragment(EDIT_CONTACT_FRAGMENT, this)
            }
        }

        addContactButton.setOnClickListener {


            //noContactsTextView = findViewById(R.id.noContactsTextView);
            (activity as ChangeFragmentListener).onChangeFragment(ADD_CONTACT_FRAGMENT, null)

            /*intent = Intent(this@MainActivity, AddContactActivity::class.java)
            startActivityForResult(intent, 1)*/

        }

        dataAdapter = DataAdapter(view.context, ArrayList<ContactBody>(), onContactClickListener)
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        }

        dataAdapter.add(ContactBody(R.drawable.ic_baseline_contact_phone_24, "name", "email"))
/*dataAdapter.add(ContactBody(R.drawable.ic_baseline_contact_phone_24, "name", "email"))
dataAdapter.add(ContactBody(R.drawable.ic_baseline_contact_phone_24, "name", "email"))*/

        checkState()
    }

    private fun checkState() {
        if (dataAdapter.isEmpty) noContactsTextView.visibility = View.VISIBLE
        else noContactsTextView.visibility = View.INVISIBLE
    }


}