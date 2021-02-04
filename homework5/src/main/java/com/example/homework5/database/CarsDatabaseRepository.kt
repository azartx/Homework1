package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.CarData
import java.util.concurrent.CompletableFuture

class CarsDatabaseRepository(context: Context) {

    private val database = CarsDatabase.init(context)

    fun addCar(car: CarData) {
        CompletableFuture.runAsync {
            database.getCarDatabaseDAO().addCarToDatabase(car)
        }
    }

    fun getCarsList(): CompletableFuture<List<CarData>> = CompletableFuture.supplyAsync<List<CarData>> {
        return@supplyAsync database.getCarDatabaseDAO().getCarsList()
    }

    fun getCar(carId: Long): CompletableFuture<CarData> = CompletableFuture.supplyAsync<CarData> {
        return@supplyAsync database.getCarDatabaseDAO().getCar(carId)
    }

    fun updateCar(carData: CarData) {
        CompletableFuture.runAsync { database.getCarDatabaseDAO().update(carData) }
    }

}