//package com.example.hseapp
//
//import android.app.Application
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Intent
//import android.net.Uri
//import android.os.Build
//import android.os.Environment
//import android.provider.Settings
//
//class BaseApp: Application() {
//
//    companion object{
//        const val CHANNEL_ID_1 = "channel_satu"
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        createNotificationChannels()
//    }
//
//    private fun createNotificationChannels() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelSatu = NotificationChannel(
//                CHANNEL_ID_1,"Channel Satu",NotificationManager.IMPORTANCE_HIGH
//            )
//            channelSatu.description="ini adalah channel satu"
//
//            val manager = getSystemService(NotificationManager::class.java)
//            manager.createNotificationChannel(channelSatu)
//        }
//    }
//}