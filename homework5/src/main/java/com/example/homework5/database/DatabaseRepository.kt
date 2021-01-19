package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData
import java.util.concurrent.Executors

class DatabaseRepository(context: Context) {

    private val executorService = Executors.newSingleThreadExecutor()
    private val database = CarsDatabase.init(context)

    /*****************************
     * Start cars database block
     *****************************/
    fun addCar(car: CarData) {
        executorService.submit { database.getCarDatabaseDAO().addCarToDatabase(car) }
    }

    fun getCarsList(): List<CarData> {
        return executorService.submit<List<CarData>> {
            database.getCarDatabaseDAO().getCarsList()
        }.get()
    }

    fun getCar(carId: Long): CarData {
        val service = executorService.submit<CarData> {
            return@submit database.getCarDatabaseDAO().getCar(carId)
        }
        return service.get()
    }

    fun updateCar(carData: CarData) {
        executorService.submit { database.getCarDatabaseDAO().update(carData) }
    }

    /*****************************
     * End car block
     *
     * Start works database block
     *****************************/

    fun getParentWorks(parentCar: String?): List<WorkData> {
        return executorService.submit<List<WorkData>> {
            database.getWorkDatabaseDAO().getParentWorks(parentCar)
        }.get()
    }

    fun getWork(workId: Long): WorkData {
        val service = executorService.submit<WorkData> {
            return@submit database.getWorkDatabaseDAO().getWork(workId)
        }
        return service.get()
    }

    fun updateWork(workData: WorkData) {
        executorService.submit { database.getWorkDatabaseDAO().update(workData) }
    }

    fun deleteWork(workData: WorkData) {
        executorService.submit { database.getWorkDatabaseDAO().delete(workData) }
    }

    fun addWorkToDatabase(workData: WorkData) {
        executorService.submit { database.getWorkDatabaseDAO().addWorkToDatabase(workData) }
    }

    /*********
     * End work block
     ************/
}