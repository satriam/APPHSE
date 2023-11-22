package com.example.hseapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hseapp.retrofit.SessionManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val CHANNEL_ID = "Channel_id_example_01"
    private val notificationId = 101
    private lateinit var sessionManager: SessionManager
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            showNotification(applicationContext, title, body)
        } else {
            val title = remoteMessage.notification?.title
            val body = remoteMessage.notification?.body
            showNotification(applicationContext, title, body)
        }
    }

    override fun onNewToken(p0: String) {
        sessionManager = SessionManager(this)

        super.onNewToken(p0)
        Log.d(TAG, "Refreshed token: $p0")
        sessionManager.saveToken("$p0")
    }

    fun showNotification(context: Context, title: String?, message: String?) {
        val ii = Intent(context, MainActivity::class.java)
        ii.data = Uri.parse("custom://" + System.currentTimeMillis())
        ii.action = "actionstring" + System.currentTimeMillis()
        ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pi = PendingIntent.getActivity(
            context, 0, ii,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(title)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true) // Allow the notification to be dismissed when tapped
                .build()

            val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, title, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(notificationId, notification)
        } else {
            notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentIntent(pi)
                .setContentTitle(title)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true) // Allow the notification to be dismissed when tapped
                .build()

            val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.notify(notificationId, notification)
        }
    }

    companion object {
        const val TAG = "Token"
    }

}