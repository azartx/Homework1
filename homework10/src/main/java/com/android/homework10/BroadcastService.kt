package com.android.homework10

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_NONE
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationCompat.CATEGORY_SERVICE
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class BroadcastService : Service() {

    private val bindService: BindService = BindService()

    override fun onCreate() {
        createNotificationChannel()
        showServiceNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.extras?.containsKey(INTENT_TAG) == true) {
            Log.i("FFFF", "MSG FROM SERVICE - CONNECT DONE! ${intent.getStringExtra(INTENT_TAG)}")
            LogData(intent.getStringExtra(INTENT_TAG)
                    ?: "error load event name", getCurrentTime()).apply {
                writeLogToFile(Gson().toJson(this))
            }
        }
        Intent("FILE_UPDATED").putExtra("qwe", "qwe").apply {
            applicationContext.sendBroadcast(this)
        }

        return START_STICKY
    }

    private fun writeLogToFile(gsonObject: String?) {
        CoroutineScope(Dispatchers.Main + Job()).launch {
            withContext(Dispatchers.Unconfined) {
                openFileOutput("log.txt", MODE_APPEND).apply {
                    write(("\n" + gsonObject).toByteArray())
                    close()
                }
            }
        }
    }

    private fun getCurrentTime(): String =
            SimpleDateFormat("dd/M/yyyy hh:mm:ss",
                    Locale.getDefault())
                    .format(Date()).toString()


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                    "broadcastChannel",
                    "notifChannel",
                    IMPORTANCE_NONE
            ).apply {
                lightColor = Color.BLUE
                lockscreenVisibility = VISIBILITY_PRIVATE
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                        .createNotificationChannel(this)
            }
        }
    }

    private fun showServiceNotification() {
        Builder(baseContext, "broadcastChannel")
                .setContentTitle("Broadcast service is running")
                .setContentText("Broadcast receiver is getting mobile events and create log for your application")
                .setCategory(CATEGORY_SERVICE)
                .setPriority(PRIORITY_MAX)
                .build().apply {
                    startForeground(110, this)
                }
    }

    interface OnUpdateListener{
        fun onFileUpdate()
    }

    override fun onBind(intent: Intent?): IBinder = bindService
    inner class BindService : Binder() {
        fun getService(): BroadcastService = this@BroadcastService
    }
}