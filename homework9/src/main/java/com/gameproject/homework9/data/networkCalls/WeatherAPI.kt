package com.gameproject.homework9.data.networkCalls

import com.gameproject.homework9.data.WeatherFromApi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/forecast")
    fun getCountryWeather(@Query("q") country: String,
                          @Query("units") units: String,
                          @Query("appid") appKey: String): Single<WeatherFromApi>
}