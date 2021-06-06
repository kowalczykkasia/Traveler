package com.example.traveler.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveler.PhotoItemDto

class LibraryViewModel : ViewModel() {
    val photoListModel = MutableLiveData<List<Pair<PhotoItemDto, Bitmap?>>?>()

    fun update(photoList: List<Pair<PhotoItemDto, Bitmap?>>?){
        photoListModel.postValue(photoList)
    }
}