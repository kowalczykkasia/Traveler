package com.example.traveler.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveler.PhotoItemDto

@Dao
abstract class PhotosDao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insert(photoItemDto: PhotoItemDto): Long

    @Query("SELECT * FROM PhotoItemDto")
    abstract fun selectAll(): List<PhotoItemDto>
}