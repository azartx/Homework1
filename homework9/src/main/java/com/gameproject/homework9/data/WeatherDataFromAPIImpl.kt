package com.gameproject.homework9.data

import io.reactivex.Single

interface WeatherDataFromAPIImpl {
    fun getWeather(country: String, appKey: String): Single<WeatherDataFromAPI>
}