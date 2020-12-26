package com.example.homework5.database

import androidx.room.*
import com.example.homework5.data.CarData

@Dao
interface CarsDatabaseDAO {

    @Query("SELECT * FROM CarData")
    fun getCarsList(): List<CarData>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: CarData)
}