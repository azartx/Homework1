package com.app.homework8_1.db

import android.content.Context
import com.app.homework8_1.ContactBody
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactsRepository(context: Context) {

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    private val database = ContactsDB.init(context)
    private val threadIO = Dispatchers.IO

    fun mainScope() = mainScope

    fun addContact(contact: ContactBody) {
        mainScope.launch {
            withContext(threadIO) {
                database.getContactsDatabaseDAO().addContactToDB(contact)
            }
        }
    }

    suspend fun getContactsList(): List<ContactBody> {
        return withContext(threadIO) {
            database.getContactsDatabaseDAO().getContactList()
        }
    }

    suspend fun getContact(id: Long): ContactBody {
        return withContext(threadIO) {
            database.getContactsDatabaseDAO().getContact(id)
        }
    }

    fun updateContact(contact: ContactBody) {
        mainScope.launch {
            withContext(threadIO) {
                database.getContactsDatabaseDAO().update(contact)
            }
        }
    }

    fun removeContact(contact: ContactBody) {
        mainScope.launch {
            withContext(threadIO) {
                database.getContactsDatabaseDAO().delete(contact)
            }
        }
    }

    fun closeDB() {
        mainScope.launch {
            withContext(threadIO) { database.close() }
        }
    }

}

class SingletonDatabase {
    companion object {
        @JvmStatic
        lateinit var contactDB: ContactsRepository

        @JvmStatic
        fun getDB(context: Context) {
            contactDB = ContactsRepository(context)
        }
    }
}