package com.gameproject.homework9.viewModels

import com.gameproject.homework9.data.WeatherFromApi

// if true then object correct for app logic, else temp is TOO MUCH (for app logic)
class WeatherUsesCase {
    fun filterTemp(weatherData: WeatherFromApi): Boolean = weatherData.list[0].main.temp < 50.0
}