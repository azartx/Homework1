package com.app.homework8_1.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.homework8_1.ContactBody

@Dao
interface ContactsDAO {

    @Query("SELECT * FROM ContactBody")
    fun getContactList(): List<ContactBody>

    @Query("SELECT * FROM ContactBody WHERE id = :contactId")
    fun getContact(contactId: Long): ContactBody

    @Delete
    fun delete(contact: ContactBody)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(contact: ContactBody)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContactToDB(contact: ContactBody)
}