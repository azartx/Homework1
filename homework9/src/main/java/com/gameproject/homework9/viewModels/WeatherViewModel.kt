package com.gameproject.homework9.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameproject.homework9.data.City
import com.gameproject.homework9.data.WeatherFromApi
import com.gameproject.homework9.data.WeatherRepository
import com.gameproject.homework9.database.Cities
import com.gameproject.homework9.database.CitiesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()
    private lateinit var citiesRepository : CitiesRepository

    private val mutableWeatherLiveData = MutableLiveData<WeatherFromApi>()
    val newsWeatherLaveData: LiveData<WeatherFromApi> = mutableWeatherLiveData

    private var mutableCitiesLiveData = MutableLiveData<Cities>()
    var citiesLaveData: LiveData<Cities> = mutableCitiesLiveData

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

    fun addCityIntoDb(context: Context, city: String) {
        citiesRepository = CitiesRepository(context)
        Cities(city).apply {
            citiesRepository.addCity(this)
            mutableCitiesLiveData.setValue(this)
        }


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}