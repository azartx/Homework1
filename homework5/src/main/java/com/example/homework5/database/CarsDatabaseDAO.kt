package com.example.homework5.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.homework5.data.CarData

@Dao
interface CarsDatabaseDAO {

    @Query("SELECT * FROM CarData")
    fun getCarsList(): List<CarData>

    @Query("SELECT * FROM CarData WHERE id = :carId")
    fun getCar(carId: Long): CarData

    @Delete
    fun delete(carData: CarData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(carData: CarData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: CarData)

}