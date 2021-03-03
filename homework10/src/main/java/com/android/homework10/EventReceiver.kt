package com.android.homework10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class EventReceiver : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {




        Log.d("FFFF", intent.action + "qweqweqweqweqwe")
    }
}