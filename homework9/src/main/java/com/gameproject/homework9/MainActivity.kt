package com.gameproject.homework9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.add
import com.gameproject.homework9.data.WeatherRepository
import com.gameproject.homework9.database.WeatherData
import com.gameproject.homework9.fragments.WeatherFragment

class MainActivity : AppCompatActivity() {

    /*val url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API KEY}"
    val apiKey = "235c69b35023827c6ac676515cbc005a"*/

    /*WeatherRepository().getTopHeadlines("Гродно").blockingGet().apply {
            val g = arrayOf(this)

            Log.i("FFFF", g[0].list[0].dt_txt)
            Log.i("FFFF", g[0].list[39].dt_txt)
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add<WeatherFragment>(R.id.rootFragment)
                .commit()




    }
}