package com.gameproject.homework9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.add
import androidx.fragment.app.replace
import com.gameproject.homework9.fragments.CityChoiceFragment
import com.gameproject.homework9.fragments.WeatherFragment
import com.gameproject.homework9.utils.Constants.ACTUAL_CITY
import com.gameproject.homework9.utils.Constants.CITY_CHOICE_FRAGMENT
import com.gameproject.homework9.utils.Constants.WEATHER_FRAGMENT
import com.gameproject.homework9.utils.Constants.actualCity

class MainActivity : AppCompatActivity(), OnChangeFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actualCity = getPreferences(MODE_PRIVATE).getString(ACTUAL_CITY, "London") ?: "London"

        supportFragmentManager.beginTransaction()
                .add<WeatherFragment>(R.id.rootFragment)
                .commit()
    }

    override fun onFragmentChange(fragmentConst: Int, bundle: Bundle?) {
        when (fragmentConst) {
            WEATHER_FRAGMENT -> supportFragmentManager.beginTransaction().replace<WeatherFragment>(R.id.rootFragment, "", bundle).commit()
            CITY_CHOICE_FRAGMENT -> supportFragmentManager.beginTransaction().replace<CityChoiceFragment>(R.id.rootFragment, "", bundle).commit()
        }
    }

    override fun onPause() {
        super.onPause()
        getPreferences(MODE_PRIVATE).edit().apply {
            putString(ACTUAL_CITY, actualCity).apply()
        }
    }

}