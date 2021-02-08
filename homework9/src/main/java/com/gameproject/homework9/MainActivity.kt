package com.gameproject.homework9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.replace
import com.gameproject.homework9.fragments.CityChoiceFragment
import com.gameproject.homework9.fragments.WeatherFragment
import com.gameproject.homework9.utils.Constants.CITY_CHOICE_FRAGMENT
import com.gameproject.homework9.utils.Constants.WEATHER_FRAGMENT

class MainActivity : AppCompatActivity(), OnChangeFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}