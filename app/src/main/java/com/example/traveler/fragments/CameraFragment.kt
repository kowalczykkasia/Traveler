package com.example.traveler.fragments

import android.Manifest
import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.traveler.*
import com.example.traveler.database.Shared
import com.example.traveler.tools.DateFormatter
import com.example.traveler.tools.NotificationService
import com.example.traveler.viewModels.CameraViewModel
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import kotlinx.android.synthetic.main.camera_layout.*
import kotlinx.android.synthetic.main.camera_layout.view.*
import java.io.*
import java.time.LocalDate
import java.util.*
import kotlin.concurrent.thread

class CameraFragment : Fragment() {

    private lateinit var viewModel: CameraViewModel
    private var bitmap: Bitmap? = null
    private var date: String? = null
    private val dateFormatter = DateFormatter()
    private lateinit var geocoder: Geocoder
    private var addresses: String? = null
    private var location: Location? = null
    private var wasSaved = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.camera_layout, container, false).apply {
            initView(this)
            geocoder = Geocoder(context, Locale.getDefault());
            (activity as? MainActivity)?.apply {
                currentLocation.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    location = it
                })
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)

    }

    private fun initView(rootView: View) {
        rootView.apply {
            context?.let { context ->
                this.flView.setOnClickListener {
                    if (ContextCompat.checkSelfPermission(context, CAMERA) == PackageManager.PERMISSION_GRANTED) openCamera()
                    else ActivityCompat.requestPermissions((activity as Activity), arrayOf(CAMERA), CAMERA_PERMISSION_GRANTED)
                }
            }
            btnSave.setOnClickListener {
                if (!wasSaved) {
                    if (etDescription.text.toString().isBlank() || bitmap == null) {
                        Toast.makeText(context, "Description or photo is empty", Toast.LENGTH_SHORT).show()
                    } else {
                        val photoItem = PhotoItemDto(
                            description = etDescription.text.toString(),
                            address = addresses ?: UNKNOWN_LOCATION,
                            date = date,
                            lat = location?.latitude,
                            lng = location?.longitude
                        )
                        savePhotoToDatabase(photoItem)
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        wasSaved = true
                    }
                }
            }
        }
    }

    private fun getAddress() {
        val lastKnownLocation = location
        lastKnownLocation?.latitude?.let { lat ->
            lastKnownLocation.longitude?.let { long ->
                addresses = "${
                    geocoder?.getFromLocation(lat, long, 1).get(0).countryName
                } ${geocoder?.getFromLocation(lat, long, 1).get(0).locality}"
                tvCity.text = addresses
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        bitmap = (data?.extras?.get("data")) as? Bitmap
        bitmap?.let {
            ivPhoto.setImageBitmap(bitmap)
            tvTip.visibility = View.GONE
            date = dateFormatter.getToday()
            tvDate.text = date
            getAddress()
        }
    }

    private fun savePhotoToDatabase(photoItemDto: PhotoItemDto) {
        thread {
            val result =
                (activity as? MainActivity)?.getRepository()?.savePhotoToDatabase(photoItemDto)
            context?.let {
                bitmap?.let { bitmap ->
                    viewModel.saveToInternalStorage(bitmap, it, result)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_GRANTED) openCamera()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}