package com.example.traveler.tools

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action
import androidx.core.content.ContextCompat
import com.example.traveler.MainActivity
import com.example.traveler.NOTIFICATION_CHANNEL_DEFAULT
import com.example.traveler.PhotoItemDto
import com.example.traveler.R
import com.example.traveler.database.Shared

class NotificationService: Service() {

    private lateinit var channel: NotificationChannel

    override fun onCreate() {
        super.onCreate()
        registerChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val photoItem = intent?.extras?.get("data") as? PhotoItemDto
        val bitmap = intent?.extras?.get("bitmap") as? Bitmap
        createNotification(photoItem, bitmap)
        return START_NOT_STICKY
    }

    private fun createNotification(photoItem: PhotoItemDto?, bitmap: Bitmap?){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("photoItemData", photoItem)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_DEFAULT)
            .setContentTitle(photoItem?.description)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle("${photoItem?.date} ${photoItem?.address}")
            .setContentText("${photoItem?.description}")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        startForeground(1, notification)
    }

    fun registerChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            channel = NotificationChannel(
                NOTIFICATION_CHANNEL_DEFAULT,
                "General",
                importance
            )
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}