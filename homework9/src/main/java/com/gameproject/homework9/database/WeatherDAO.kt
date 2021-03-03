package com.gameproject.homework9.database

import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDAO {
    @Query("SELECT * FROM Cities")
    fun getCitiesList(): List<Cities>

    @Query("SELECT * FROM Cities WHERE cityId = :cityId")
    fun getCity(cityId: Int): Cities

    @Delete
    fun delete(city: Cities)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(city: Cities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCityToDB(city: Cities)
}