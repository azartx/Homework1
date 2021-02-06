package com.gameproject.homework9.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameproject.homework9.data.WeatherFromApi
import com.gameproject.homework9.data.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private val mutableWeatherLiveData = MutableLiveData<WeatherFromApi>()
    val newsWeatherLaveData: LiveData<WeatherFromApi> = mutableWeatherLiveData

    private val errorMutableWeatherLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = errorMutableWeatherLiveData


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun fetchWeather(country: String) {
        weatherRepository.getWeather(country)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { newsList -> mutableWeatherLiveData.value = newsList },
                        { error -> errorMutableWeatherLiveData.value = "Error: " + error.message }
                ).also {
                    compositeDisposable.add(it)
                }
    }
}