package com.example.homework5.database

import androidx.room.*
import com.example.homework5.data.WorkData

@Dao
interface WorksDatabaseDAO {

    @Query("SELECT * FROM WorkData")
    fun getCarsList(): List<WorkData>

    @Delete
    fun delete(carData: WorkData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCarToDatabase(entity: WorkData)
}