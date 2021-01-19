package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData
import java.util.concurrent.CompletableFuture

class DatabaseRepository(context: Context) {

    private val database = CarsDatabase.init(context)

    /*****************************
     * Start cars database block
     *****************************/
    fun addCar(car: CarData) {
        CompletableFuture.runAsync {
            database.getCarDatabaseDAO().addCarToDatabase(car)
        }
    }

    fun getCarsList(): List<CarData> {
        return CompletableFuture.supplyAsync<List<CarData>> {
            database.getCarDatabaseDAO().getCarsList()
        }.get()
    }

    fun getCar(carId: Long): CarData {
        val service = CompletableFuture.supplyAsync<CarData> {
            return@supplyAsync database.getCarDatabaseDAO().getCar(carId)
        }
        return service.get()
    }

    fun updateCar(carData: CarData) {
        CompletableFuture.runAsync { database.getCarDatabaseDAO().update(carData) }
    }

    /*****************************
     * End car block
     *
     * Start works database block
     *****************************/

    fun getParentWorks(parentCar: String?): List<WorkData> {
        return CompletableFuture.supplyAsync<List<WorkData>> {
            database.getWorkDatabaseDAO().getParentWorks(parentCar)
        }.get()
    }

    fun getWork(workId: Long): WorkData {
        val service = CompletableFuture.supplyAsync<WorkData> {
            return@supplyAsync database.getWorkDatabaseDAO().getWork(workId)
        }
        return service.get()
    }

    fun updateWork(workData: WorkData) {
        CompletableFuture.runAsync { database.getWorkDatabaseDAO().update(workData) }
    }

    fun deleteWork(workData: WorkData) {
        CompletableFuture.runAsync { database.getWorkDatabaseDAO().delete(workData) }
    }

    fun addWorkToDatabase(workData: WorkData) {
        CompletableFuture.runAsync { database.getWorkDatabaseDAO().addWorkToDatabase(workData) }
    }

    /*********
     * End work block
     ************/
}