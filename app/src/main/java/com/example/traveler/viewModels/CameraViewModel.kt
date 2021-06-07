package com.example.traveler.viewModels

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveler.PhotoItemDto
import com.example.traveler.database.Shared
import kotlinx.coroutines.launch
import java.io.*
import kotlin.concurrent.thread

class CameraViewModel : ViewModel() {
    val saved = MutableLiveData<Boolean>()

    fun saveToInternalStorage(bitmapImage: Bitmap, context: Context, result: Long?): String? {
        val contextWrapper = ContextWrapper(context)
        val directory: File = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE)
        val filePath = File(directory, "photo${result}.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(filePath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            saved.postValue(true)
        } catch (e: Exception) {
            e.printStackTrace()
            saved.postValue(false)
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }
}