package com.example.homework5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData

@Database(entities = [CarData::class, WorkData::class], version = 1)
abstract class CarsDatabase : RoomDatabase() {

    abstract fun getCarDatabaseDAO(): CarsDatabaseDAO
    abstract fun getWorkDatabaseDAO(): WorksDatabaseDAO

    companion object {
        fun init(context: Context) = Room.databaseBuilder(context, CarsDatabase::class.java, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build()

    }
}