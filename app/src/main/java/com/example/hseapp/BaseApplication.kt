package com.example.hseapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BaseApplication : Application(){

    companion object{
        const val CHANNEL_1_ID = "channel1"
        const val CHANNEL_2_ID = "channel2"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel Satu",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "channel1"

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel1)
        }
    }
}