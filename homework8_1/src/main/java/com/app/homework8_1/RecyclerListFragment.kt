package com.app.homework8_1

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.homework8_1.Constants.Companion.ADD_CONTACT_FRAGMENT
import com.app.homework8_1.Constants.Companion.EDIT_CONTACT_FRAGMENT
import com.app.homework8_1.db.ContactsDAO
import com.app.homework8_1.db.ContactsDB

class RecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {

    private lateinit var contactDB: ContactsDAO
    private lateinit var recyclerView: RecyclerView
    private lateinit var noContactsTextView: TextView
    private lateinit var onContactClickListener: DataAdapter.OnContactClickListener
    private lateinit var addContactButton: ImageButton
    private lateinit var dataAdapter: DataAdapter
    private lateinit var searchView: SearchView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)
        noContactsTextView = view.findViewById(R.id.noContactsTextView)
        addContactButton = view.findViewById(R.id.addContactButton)

        contactDB = ContactsDB.init(view.context).getContactsDatabaseDAO()

        onContactClickListener = DataAdapter.OnContactClickListener { contactBody: ContactBody?, _ ->
            Bundle().apply {
                putLong("contactId", contactBody?.id ?: 0)
                (activity as ChangeFragmentListener).onChangeFragment(EDIT_CONTACT_FRAGMENT, this)
            }
        }

        addContactButton.setOnClickListener {
            (activity as ChangeFragmentListener).onChangeFragment(ADD_CONTACT_FRAGMENT, null)
        }

        dataAdapter = DataAdapter(view.context, ArrayList(), onContactClickListener)
        recyclerView.apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        }

        searchUtil() //не доделан

        getData()
        checkState()
    }

    private fun searchUtil() {
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                dataAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun checkState() {
        if (dataAdapter.isEmpty) noContactsTextView.visibility = View.VISIBLE
        else noContactsTextView.visibility = View.INVISIBLE
    }

    private fun getData() {
        dataAdapter.contacts.addAll(contactDB.getContactList())
        dataAdapter.notifyDataSetChanged()
    }
}