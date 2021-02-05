package com.gameproject.homework9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API KEY}"
    val apiKey = "235c69b35023827c6ac676515cbc005a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}