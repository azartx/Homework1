/*
package com.example.homework5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homework5.data.WorkData

@Database(entities = [WorkData::class], version = 1)
abstract class WorkDatabase : RoomDatabase() {

    abstract fun getWorkDatabaseDAO(): WorksDatabaseDAO

    companion object {
        fun init(context: Context) = Room.databaseBuilder(context, WorkDatabase::class.java, "workDatabase").allowMainThreadQueries().build()

    }
}*/
