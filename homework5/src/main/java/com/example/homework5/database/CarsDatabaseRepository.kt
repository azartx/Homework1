package com.example.homework5.database

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.homework5.data.CarData
import java.util.concurrent.Executors

class CarsDatabaseRepository(context: Context) {

    private val executorService = Executors.newSingleThreadExecutor()
    private val database = CarsDatabase.init(context)
    private val mainExecutor = ContextCompat.getMainExecutor(context)

    fun addCar(car: CarData) {
        executorService.submit {
            database.getCarDatabaseDAO().addCarToDatabase(car)
        }
    }

    fun getCarsList(callbackListener: (List<CarData>) -> Unit) {
        executorService.submit<List<CarData>> {
            database.getCarDatabaseDAO().getCarsList().apply {
                mainExecutor.execute {
                    callbackListener.invoke(this)
                }
            }
        }
    }

    fun getCar(carId: Long, callbackListener: (CarData) -> Unit) {
        executorService.submit<CarData> {
            database.getCarDatabaseDAO().getCar(carId).apply {
                mainExecutor.execute {
                    callbackListener.invoke(this)
                }
            }
        }
    }

    fun updateCar(carData: CarData) {
        executorService.submit { database.getCarDatabaseDAO().update(carData) }
    }
}