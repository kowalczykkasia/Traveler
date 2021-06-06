package com.example.traveler.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.traveler.FONT_SIZE
import com.example.traveler.PhotoItemDto
import com.example.traveler.R
import com.example.traveler.database.Shared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_dialog.view.*

class DialogFragment(private val photoDetails: Pair<PhotoItemDto, Bitmap?>) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false).apply {
            initView(this)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    private fun initView(rootView: View){
        rootView.apply {
            tvDescription.textSize =  resources.getDimension(Shared.sharedPrefs?.getInt(FONT_SIZE, R.dimen.textSize_20) ?: R.dimen.textSize_20)
            ivPhoto.setImageBitmap(photoDetails.second)
            tvDescription.text = photoDetails.first.description
            tvCity.text = photoDetails.first.address
            tvDate.text = photoDetails.first.date
        }
    }
}