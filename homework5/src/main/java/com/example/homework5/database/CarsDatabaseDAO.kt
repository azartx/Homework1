package com.example.homework5.database

import androidx.room.*
import com.example.homework5.data.CarData

@Dao
interface CarsDatabaseDAO {

    @Query("SELECT * FROM CarData")
    fun getCarsList(): List<CarData>

    @Query("SELECT * FROM CarData WHERE id = :carId LIMIT 1")
    fun getCar(carId: Long): CarData

    @Delete
    fun delete(carData: CarData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(carData: CarData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: CarData)


}