package com.example.traveler.tools

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.traveler.MainActivity
import com.example.traveler.NOTIFICATION_CHANNEL_DEFAULT
import com.example.traveler.PhotoItemDto
import com.example.traveler.R
import com.example.traveler.database.Shared
import com.google.android.gms.location.GeofencingEvent
import kotlin.concurrent.thread

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofenvingEvent = GeofencingEvent.fromIntent(intent)
        if(geofenvingEvent.hasError()){
            return
        }
        geofenvingEvent.triggeringGeofences.forEach {
            thread {
                val photoItem = Shared.repository?.getPhotosById(it.requestId.toLong())
                val bitmap = Shared.repository?.getPathForImage(photoItem?.id)
                val intent = Intent(context, NotificationService::class.java)
                intent.putExtra("data", photoItem)
                intent.putExtra("bitmap", bitmap)
                context.startService(intent)
            }
        }
    }

}










