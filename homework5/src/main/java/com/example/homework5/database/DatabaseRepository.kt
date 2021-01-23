package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DatabaseRepository(context: Context) {

    private val database = CarsDatabase.init(context)

    /*****************************
     * Start cars database block
     *****************************/
    fun addCar(car: CarData) {
        Single.create<CarData> {
            database.getCarDatabaseDAO().addCarToDatabase(car)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getCarsList(): List<CarData> {
        return Single.create<List<CarData>> {
            val list = database.getCarDatabaseDAO().getCarsList()
            it.onSuccess(list)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }


    fun getCar(carId: Long): CarData {
        return Single.create<CarData> {
            val car = database.getCarDatabaseDAO().getCar(carId)
            it.onSuccess(car)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun updateCar(carData: CarData) {
        Single.create<CarData> {
            database.getCarDatabaseDAO().update(carData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    /*****************************
     * End car block
     *
     * Start works database block
     *****************************/

    fun getParentWorks(parentCar: String?): List<WorkData> {
        return Single.create<List<WorkData>> {
            val list = database.getWorkDatabaseDAO().getParentWorks(parentCar)
            it.onSuccess(list)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun getWork(workId: Long): WorkData {
        return Single.create<WorkData> {
            val work = database.getWorkDatabaseDAO().getWork(workId)
            it.onSuccess(work)
        }.subscribeOn(Schedulers.io())
                .blockingGet()
    }

    fun updateWork(workData: WorkData) {
        Single.create<WorkData> {
            database.getWorkDatabaseDAO().update(workData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun deleteWork(workData: WorkData) {
        Single.create<WorkData> {
            database.getWorkDatabaseDAO().delete(workData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun addWorkToDatabase(workData: WorkData) {
        Single.create<WorkData> {
            database.getWorkDatabaseDAO().addWorkToDatabase(workData)
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }

    /*********
     * End work block
     ************/
}