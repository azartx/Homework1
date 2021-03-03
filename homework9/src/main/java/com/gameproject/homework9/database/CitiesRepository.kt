package com.gameproject.homework9.database

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitiesRepository(context: Context) {

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())
    private val database = WeatherDB.init(context)
    private val threadIO = Dispatchers.IO

    fun mainScope() = mainScope

    suspend fun getCitiesList(): List<Cities> {
        return withContext(threadIO) {
            database.getCitiesDAO().getCitiesList()
        }
    }

    suspend fun addCityGetList(city: Cities): List<Cities> {
        return withContext(threadIO) {
            database.getCitiesDAO().addCityToDB(city)
            return@withContext database.getCitiesDAO().getCitiesList()
        }
    }

    suspend fun removeCityGetList(city: Cities): List<Cities> {
        return withContext(threadIO) {
            database.getCitiesDAO().delete(city)
            return@withContext database.getCitiesDAO().getCitiesList()
        }
    }

}