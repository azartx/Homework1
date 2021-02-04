package com.example.homework5.database

import android.content.Context
import com.example.homework5.data.WorkData
import java.util.concurrent.CompletableFuture

class WorksDatabaseRepository(context: Context) {

    private val database = CarsDatabase.init(context)

    fun getParentWorks(parentCar: String?): CompletableFuture<List<WorkData>> = CompletableFuture.supplyAsync<List<WorkData>> {
        database.getWorkDatabaseDAO().getParentWorks(parentCar)
    }

    fun getWork(workId: Long): CompletableFuture<WorkData> = CompletableFuture.supplyAsync<WorkData> {
        return@supplyAsync database.getWorkDatabaseDAO().getWork(workId)
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

}