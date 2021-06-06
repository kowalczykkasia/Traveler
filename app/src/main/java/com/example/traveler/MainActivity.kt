package com.example.traveler

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.traveler.database.Repository
import com.example.traveler.database.Shared
import com.example.traveler.databinding.ActivityMainBinding
import com.example.traveler.fragments.CameraFragment
import com.example.traveler.fragments.DialogFragment
import com.example.traveler.fragments.HomeFragment
import com.example.traveler.tools.MyReceiver
import com.google.android.gms.location.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val geofencingClient by lazy { LocationServices.getGeofencingClient(this) }
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }
    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                currentLocation.value = p0.lastLocation
            }
        }
    }

    val currentLocation = MutableLiveData<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        val photoItem = intent?.extras?.get("photoItemData") as? PhotoItemDto
        if (photoItem != null) DialogFragment(Pair(photoItem, Shared.repository?.getPathForImage(photoItem.id))).show(supportFragmentManager, DialogFragment::class.java.name)
        showFragment(HomeFragment())
        initShared()
        if (!hasPermissions()) ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        else {
            if (Shared.sharedPrefs?.getBoolean(NOTIFICATIONS, true) == true) {
                listenerForLocationUpdates()
            } //todo else remove it
        }
    }

    @SuppressLint("MissingPermission")
    fun listenerForLocationUpdates() {
        val interval = 1500L
        val fastestInterval = 500L
        val locationRequest = LocationRequest.create().apply {
            this.interval = interval
            this.fastestInterval = fastestInterval
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun removeLocationCallback() = fusedLocationClient.removeLocationUpdates(locationCallback)

    private fun initShared() {
        Shared.repository = Repository(applicationContext)
        Shared.sharedPrefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    }

    private fun initView() {
        binding.apply {
            ivHome.setOnClickListener { showFragment(CameraFragment()) }
            ivFav.setOnClickListener { showToastComingSoon() }
            ivSearch.setOnClickListener { showToastComingSoon() }
            ivProfile.setOnClickListener {
                showFragment(HomeFragment())
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flContainer, fragment)
        ft.commit()
    }

    private fun showToastComingSoon() {
        Toast.makeText(baseContext, "Coming soon", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        //nothing to do
    }

    fun getRepository() = Shared.repository

    private fun hasPermissions(): Boolean =
        PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            listenerForLocationUpdates()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
