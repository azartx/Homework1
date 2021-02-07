package com.app.homework8_1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.homework8_1.ContactBody

@Database(entities = [ContactBody::class], version = 1)
abstract class ContactsDB: RoomDatabase() {

    abstract fun getContactsDatabaseDAO(): ContactsDAO

    companion object{
        fun init(context: Context) = Room.databaseBuilder(context, ContactsDB::class.java, "contactsDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

}