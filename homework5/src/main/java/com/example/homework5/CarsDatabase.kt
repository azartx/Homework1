package com.example.homework5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CarsInfoDatabase::class], version = 1)
abstract class CarsDatabase : RoomDatabase() {

    abstract fun getCarDatabaseDAO(): CarsDatabaseDAO

    companion object {
        fun init(context: Context) = Room.databaseBuilder(context, CarsDatabase::class.java, "database").build()

    }
}