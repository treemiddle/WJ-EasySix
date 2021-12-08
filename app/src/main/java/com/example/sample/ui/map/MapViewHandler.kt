package com.example.sample.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.example.sample.R
import com.example.sample.utils.CAMERA_ZOOM_LEVEL
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapViewHandler(
    private val context: Context?,
    private val fragment: MapFragment,
    private val viewModel: MapViewModel
) : OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private val location by lazy {
        getCurrentLocation()
    }

    private val locationManager: LocationManager by lazy {
        context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private var googleMap: GoogleMap? = null
    private lateinit var supportMapFragment: SupportMapFragment

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap.apply {
            setOnCameraIdleListener(this@MapViewHandler)
        }

        viewModel.onMapReady(true)
    }

    override fun onCameraIdle() {
        val cameraLocation = googleMap?.cameraPosition?.target

        if (cameraLocation?.latitude != 0.0 && cameraLocation?.longitude != 0.0) {
            viewModel.checkCurrentLocation(
                lat = cameraLocation?.latitude,
                lng = cameraLocation?.longitude
            )
        }
    }

    fun initMap() {
        supportMapFragment = fragment.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    }

    fun setMap() {
        supportMapFragment.getMapAsync(this)
        viewModel.isReadyCheck(
            lat = location?.latitude ?: 0.0,
            lng = location?.longitude ?: 0.0
        )
    }

    fun moveCameraPosition(latlng: Pair<Double, Double>) {
        googleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latlng.first, latlng.second),
                CAMERA_ZOOM_LEVEL
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Location? {
        val providers = locationManager.getProviders(true)
        var currentLocation: Location? = null

        for (item in providers) {
            val location = locationManager.getLastKnownLocation(item) ?: continue

            if (currentLocation == null || location.accuracy < currentLocation.accuracy) {
                currentLocation = location
            }
        }

        return currentLocation
    }

}