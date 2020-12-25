package com.example.homework5

import androidx.room.*

@Dao
interface CarsDatabaseDAO {

    @Query("SELECT * FROM CarData")
    fun getCarsList(): List<CarData>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: CarData)
}