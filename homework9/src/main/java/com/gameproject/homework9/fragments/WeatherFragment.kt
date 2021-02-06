package com.gameproject.homework9.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gameproject.homework9.R
import com.gameproject.homework9.data.WeatherFromApi
import com.gameproject.homework9.databinding.FragmentWeatherBinding
import com.gameproject.homework9.viewModels.WeatherViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var binding: FragmentWeatherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherBinding.bind(view)

        ViewModelProvider(this).get(WeatherViewModel::class.java).also {
            it.newsWeatherLaveData.observe(viewLifecycleOwner, { data -> showContent(data) })
            it.errorLiveData.observe(viewLifecycleOwner, { errorMsg -> showError(errorMsg) })
            it.fetchWeather("Лида")
        }

        binding.changeCountry.setOnClickListener {
            Toast.makeText(view.context, "asdasd", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showContent(weather: WeatherFromApi) {

        val dateRegex = "^\\t{11}\\w"

        binding.apply {
            countryName.text = weather.city.name

            currentData.text = weather.list[0].dt_txt
            degrees.text = weather.list[0].main.temp.toInt().toString()
            Log.i("FFFF", weather.list[9].dt_txt)
            day2.text = weather.list[8].dt_txt.replace(Regex(dateRegex), "")
        }

    }

    private fun showError(errorMessage: String) {
        when (errorMessage) {
            "Error: HTTP 404 Not Found" -> Snackbar.make(this.requireView(),
                    "Название города введено не верно!",
                    Snackbar.LENGTH_LONG).show()
            else -> Snackbar.make(this.requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
        }
    }

}