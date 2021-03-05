package com.android.homework10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider

const val INTENT_TAG = "intent tag"

/*
class EventReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!intent.action.equals("FILE_UPDATED")){
            context.startService(Intent(context, BroadcastService::class.java).putExtra(INTENT_TAG, intent.action))
        }else if (intent.action.equals("FILE_UPDATED")){
                Log.i("FFFF", "!!!!!!!!!!!!!!!!!!!!!!!!")
        }
    }
}*/
