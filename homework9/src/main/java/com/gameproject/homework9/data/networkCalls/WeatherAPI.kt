package com.gameproject.homework9.data.networkCalls

import com.gameproject.homework9.data.WeatherDataFromAPI
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getCountryWeather(@Query("q") country: String, @Query("appid") appKey: String): Single<WeatherDataFromAPI>
}