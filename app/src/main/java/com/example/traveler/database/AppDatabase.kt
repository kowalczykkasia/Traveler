package com.example.traveler.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.traveler.DATABASE_NAME
import com.example.traveler.DATABASE_VERSION
import com.example.traveler.PhotoItemDto
import com.example.traveler.tools.Converter

@Database(
    entities = [PhotoItemDto::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract val photosDao: PhotosDao

    companion object{
        fun open(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
}