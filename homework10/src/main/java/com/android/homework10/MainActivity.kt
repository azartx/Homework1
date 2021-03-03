package com.android.homework10

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonWriter
import com.google.gson.Gson
import java.io.FileWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, BroadcastService::class.java))






        val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss",
                Locale.getDefault())
                .format(Date()).toString()


        openFileOutput("appLog.txt", MODE_APPEND).apply {
            write(("\n" + time).toByteArray())
            close()
        }



    }
}