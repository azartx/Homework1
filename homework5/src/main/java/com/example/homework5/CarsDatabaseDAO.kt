package com.example.homework5

import androidx.room.*

@Dao
interface CarsDatabaseDAO {

    @Query("SELECT * FROM CarsInfoDatabase")
    fun getCarsList(): List<CarsInfoDatabase>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: CarsInfoDatabase)
}