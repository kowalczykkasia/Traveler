package com.example.traveler

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

const val CAMERA_PERMISSION_GRANTED = 1
const val LOCATION_PERMISSION_GRANTED = 2
const val LOCATION_BACKGROUND_PERMISSION_GRANTED = 4

const val DEFAULT_TAB_COUNT = 2

const val NOTIFICATIONS = "notificationEnabled"
const val DISTANCE = "distance"
const val FONT_SIZE = "fontSize"

const val DATABASE_NAME = "photosdb"
const val DATABASE_VERSION = 2

const val UNKNOWN_LOCATION = "Unknown location"

var PERMISSION_ALL = 7

@RequiresApi(Build.VERSION_CODES.Q)
var PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_BACKGROUND_LOCATION
)

const val NOTIFICATION_CHANNEL_DEFAULT = "com.example.services.DEFAULT"