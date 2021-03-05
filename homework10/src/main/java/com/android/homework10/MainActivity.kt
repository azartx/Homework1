package com.android.homework10

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.IBinder
import android.util.JsonWriter
import android.util.Log
import android.widget.Toast
import com.android.homework10.Constants.STATE_APP
import com.android.homework10.Constants.STATE_APP_KEY
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onStart() {
        STATE_APP = true
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBroadcastReceiver()

    }

    fun qweqwe() {
        Toast.makeText(applicationContext, "asdasdasd", Toast.LENGTH_SHORT).show()
    }

    private fun initBroadcastReceiver() {
        val filter = IntentFilter().apply {
            addAction("FILE_UPDATED")
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (!intent?.action.equals("FILE_UPDATED")) {
                    context?.startService(Intent(context, BroadcastService::class.java).putExtra(INTENT_TAG, intent?.action))
                } else if (intent?.action.equals("FILE_UPDATED") && STATE_APP) {
                    qweqwe()

                }
            }
        }
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        STATE_APP = false
        getPreferences(MODE_PRIVATE).edit().apply {
            putBoolean(STATE_APP_KEY, STATE_APP)
        }.apply()
        super.onDestroy()
    }
}
