package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.CarData
import java.util.concurrent.Executors

class DatabaseRepository(context: Context) {

    private val executorService = Executors.newSingleThreadExecutor()
    private val database = CarsDatabase.init(context)

    fun addCar(car: CarData) {
        executorService.submit {
            database.getCarDatabaseDAO().addCarToDatabase(car)
        }
    }
}