package com.example.sample.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentMapBinding
import com.example.sample.ui.MainViewModel
import com.example.sample.utils.CAMERA_ZOOM_LEVEL
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private val viewModel by viewModels<MapViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()
    private var googleMap: GoogleMap? = null

    private val mapViewHandler: MapViewHandler by lazy {
        MapViewHandler()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value == true }

        if (granted) {
            setMap()
        }
    }

    private val checkPermissionList = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun bindViews() {
        binding.vm = viewModel
    }

    override fun initObserving() {
        with(viewModel) {
            moveCamera.observe(viewLifecycleOwner, {
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.first, it.second),
                        CAMERA_ZOOM_LEVEL
                    )
                )
            })

        }

        with(activityViewModel) {

        }
    }

    override fun initData() {
        locationPermissionRequest.launch(checkPermissionList)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap.apply {
            setOnCameraIdleListener(this@MapFragment)
        }

        viewModel.onMapReady(true)
    }

    override fun onCameraIdle() {
        val lat = googleMap?.cameraPosition?.target?.latitude
        val lng = googleMap?.cameraPosition?.target?.longitude

        if (lat != 0.0 && lng != 0.0) {
            viewModel.checkCurrentLocation(
                lat = googleMap?.cameraPosition?.target?.latitude,
                lng = googleMap?.cameraPosition?.target?.longitude
            )
        }
    }

    private fun setMap() {
        val location = getCurrentLocation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        viewModel.isReadyCheck(
            lat = location?.latitude ?: 0.0,
            lng = location?.longitude ?: 0.0
        )
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Location? {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Returns a list of the names of available location providers.
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