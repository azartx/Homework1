package com.gameproject.homework9.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameproject.homework9.data.WeatherFromApi
import com.gameproject.homework9.data.WeatherRepository
import com.gameproject.homework9.database.Cities
import com.gameproject.homework9.database.CitiesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()
    private lateinit var citiesRepository: CitiesRepository

    private val mutableWeatherLiveData = MutableLiveData<WeatherFromApi>()
    val newsWeatherLaveData: LiveData<WeatherFromApi> = mutableWeatherLiveData

    private var mutableCitiesLiveData = MutableLiveData<List<Cities>>()
    var citiesLaveData: LiveData<List<Cities>> = mutableCitiesLiveData

    private val errorMutableWeatherLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = errorMutableWeatherLiveData

    private val tooMachDegreesMutableWeatherLiveData = MutableLiveData<String>()
    val tooMachDegreesLiveData: LiveData<String> = tooMachDegreesMutableWeatherLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun fetchWeather(country: String) {
        weatherRepository.getWeather(country)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { weather ->
                            if (WeatherUsesCase().filterTemp(weather)) {
                                mutableWeatherLiveData.value = weather
                            } else {
                                tooMachDegreesMutableWeatherLiveData.value = "This country is TOO HAT!!"
                            }
                        },
                        { error -> errorMutableWeatherLiveData.value = "Error: " + error.message }
                ).also {
                    compositeDisposable.add(it)
                }
    }

    fun addCityIntoDb(context: Context, city: String) {
        citiesRepository = CitiesRepository(context)
        citiesRepository.mainScope().launch {
            mutableCitiesLiveData.value = citiesRepository.addCityGetList(Cities(city))
        }
    }

    fun loadCityList(context: Context) {
        citiesRepository = CitiesRepository(context)
        citiesRepository.mainScope().launch {
            mutableCitiesLiveData.value = citiesRepository.getCitiesList()
        }
    }

    fun deleteCityUpdateList(context: Context, city: Cities) {
        citiesRepository = CitiesRepository(context)
        citiesRepository.mainScope().launch {
            mutableCitiesLiveData.value = citiesRepository.removeCityGetList(city)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}