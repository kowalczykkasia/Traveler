package com.example.traveler.database

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.traveler.PhotoItemDto
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import kotlin.concurrent.thread


class Repository(private val context: Context) {

    private var database: AppDatabase? = null

    init {
        database = AppDatabase.open(context)
    }

    fun getPathForImage(id: Long?): Bitmap? {
        try {
            val contextWrapper = ContextWrapper(context)
            val directory: File = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE)
            return BitmapFactory.decodeStream(FileInputStream(File(directory, "photo${id}.jpg")))
        }catch (e: IOException){
            e.printStackTrace()
        }
        return null
    }

    fun savePhotoToDatabase(photoItemDto: PhotoItemDto) = database?.photosDao?.insert(photoItemDto)

    fun getAllPhotos() = database?.photosDao?.selectAll()

    fun getPhotosById(id: Long?): PhotoItemDto? = database?.photosDao?.selectAll()?.firstOrNull { it.id == id }
}