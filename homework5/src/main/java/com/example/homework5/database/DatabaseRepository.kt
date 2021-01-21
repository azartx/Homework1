package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseRepository(context: Context) {

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    private val database = CarsDatabase.init(context)
    private val threadIO = Dispatchers.IO

    fun mainScope() = mainScope

    /*****************************
     * Start cars database block
     *****************************/
    fun addCar(car: CarData) {
        mainScope.launch {
            withContext(threadIO) {
                database.getCarDatabaseDAO().addCarToDatabase(car)
            }
        }
    }

    suspend fun getCarsList(): List<CarData> {
        return withContext(threadIO) {
            database.getCarDatabaseDAO().getCarsList()
        }
    }

    suspend fun getCar(carId: Long): CarData {
        return withContext(threadIO) {
            database.getCarDatabaseDAO().getCar(carId)
        }
    }

    fun updateCar(carData: CarData) {
        mainScope.launch {
            withContext(threadIO) {
                database.getCarDatabaseDAO().update(carData)
            }
        }
    }

    /*****************************
     * End car block
     *
     * Start works database block
     *****************************/

    suspend fun getParentWorks(parentCar: String?): List<WorkData> {
        return withContext(threadIO) {
            database.getWorkDatabaseDAO().getParentWorks(parentCar)
        }
    }

    suspend fun getWork(workId: Long): WorkData {
        return withContext(threadIO) {
            database.getWorkDatabaseDAO().getWork(workId)
        }
    }

    fun updateWork(workData: WorkData) {
        mainScope.launch {
            withContext(threadIO) {
                database.getWorkDatabaseDAO().update(workData)
            }
        }
    }

    fun deleteWork(workData: WorkData) {
        mainScope.launch {
            withContext(threadIO) {
                database.getWorkDatabaseDAO().delete(workData)
            }
        }
    }

    fun addWorkToDatabase(workData: WorkData) {
        mainScope.launch {
            withContext(threadIO) {
                database.getWorkDatabaseDAO().addWorkToDatabase(workData)
            }
        }
    }

    /*********
     * End work block
     ************/
}