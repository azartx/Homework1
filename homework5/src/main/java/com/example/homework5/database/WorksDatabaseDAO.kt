package com.example.homework5.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.homework5.data.WorkData

@Dao
interface WorksDatabaseDAO {

    @Query("SELECT * FROM WorkData")
    fun getWorksList(): List<WorkData>

    @Query("SELECT * FROM WorkData WHERE parentCar LIKE :parentCar")
    fun getParentWorks(parentCar: String?): List<WorkData>

    @Query("SELECT * FROM WorkData WHERE id = :workId")
    fun getWork(workId: Long): WorkData

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(workData: WorkData)

    @Delete
    fun delete(workData: WorkData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWorkToDatabase(entity: WorkData)

}