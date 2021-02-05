package com.gameproject.homework9.data

import com.google.gson.annotations.SerializedName

data class WeatherDataFromAPI(
        @SerializedName("coord") val coord: Coord,
        @SerializedName("weather") val weather: List<Weather>,
        @SerializedName("base") val base: String,
        @SerializedName("main") val weatherMain: WeatherMain,
        @SerializedName("visibility") val visibility: Int,
        @SerializedName("wind") val wind: Wind,
        @SerializedName("clouds") val clouds: Clouds,
        @SerializedName("dt") val dt: Int,
        @SerializedName("sys") val sys: Sys,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("cod") val cod: Int
)

data class Clouds(@SerializedName("all") val all: Int)

data class Coord(
        @SerializedName("lon") val lon: Double,
        @SerializedName("lat") val lat: Double
)

data class WeatherMain(
        @SerializedName("temp") val temp: Double,
        @SerializedName("pressure") val pressure: Int,
        @SerializedName("humidity") val humidity: Int,
        @SerializedName("temp_min") val temp_min: Double,
        @SerializedName("temp_max") val temp_max: Double
)

data class Sys(
        @SerializedName("type") val type: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("message") val message: Double,
        @SerializedName("country") val country: String,
        @SerializedName("sunrise") val sunrise: Int,
        @SerializedName("sunset") val sunset: Int
)

data class Weather(
        @SerializedName("id") val id: Int,
        @SerializedName("main") val main: String,
        @SerializedName("description") val description: String,
        @SerializedName("icon") val icon: String
)

data class Wind(
        @SerializedName("speed") val speed: Double,
        @SerializedName("deg") val deg: Int
)