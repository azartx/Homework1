package com.gameproject.homework9.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cities::class], version = 1)
abstract class WeatherDB : RoomDatabase() {
    abstract fun getCitiesDAO(): WeatherDAO

    companion object {
        fun init(context: Context) = Room.databaseBuilder(context, WeatherDB::class.java, "CitiesDB")
                .build()
    }

}