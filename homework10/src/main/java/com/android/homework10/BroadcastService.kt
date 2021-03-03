package com.android.homework10

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder

class BroadcastService : Service() {

    private val bindService: BindService = BindService()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }

        registerReceiver(EventReceiver(), intentFilter)



        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = bindService
    inner class BindService : Binder() {
        // if need to call service into the activity on some els, need to use this class
    }
}