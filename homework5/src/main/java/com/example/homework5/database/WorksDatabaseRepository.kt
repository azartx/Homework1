package com.example.homework5.database

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.homework5.data.WorkData
import java.util.concurrent.Executors

class WorksDatabaseRepository(context: Context) {

    private val executorService = Executors.newSingleThreadExecutor()
    private val database = CarsDatabase.init(context)
    private val mainExecutor = ContextCompat.getMainExecutor(context)

    fun getParentWorks(parentCar: String?, callbackListener: (List<WorkData>) -> Unit) {
        executorService.submit<List<WorkData>> {
            database.getWorkDatabaseDAO().getParentWorks(parentCar).apply {
                mainExecutor.execute {
                    callbackListener.invoke(this)
                }
            }
        }
    }

    fun getWork(workId: Long, callbackListener: (WorkData) -> Unit) {
        executorService.submit<WorkData> {
            database.getWorkDatabaseDAO().getWork(workId).apply {
                mainExecutor.execute {
                    callbackListener.invoke(this)
                }
            }
        }
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
}