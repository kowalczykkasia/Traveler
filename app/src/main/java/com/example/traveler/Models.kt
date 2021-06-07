package com.example.traveler

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate

@Entity(tableName = "PhotoItemDto")
data class PhotoItemDto(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val description: String,
    val address: String?,
    val date: String?,
    val lat: Double?,
    val lng: Double?
): Serializable{
    constructor() : this(0L, "","","",0.0,0.0)
}