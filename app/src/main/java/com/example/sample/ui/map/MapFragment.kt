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
import com.example.sample.utils.makeLog
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment :
    BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveListener
{

    private val viewModel by viewModels<MapViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()
    private var googleMap: GoogleMap? = null

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value == true }

        // * 이 때 사용자가 위치 사용권한을 허용하지 않는 경우는 고려하지 않아도 좋습니다.
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
                        16f
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
            setOnCameraMoveListener(this@MapFragment)
        }

        viewModel.onMapReady(true)
    }

    // 카메라 이동 중지되고 사용자와 인터랙션이 없을 때
    override fun onCameraIdle() {
        viewModel.checkCurrentLocation(
            lat = googleMap?.cameraPosition?.target?.latitude,
            lng = googleMap?.cameraPosition?.target?.longitude
        )
    }

    override fun onCameraMove() {
        makeLog(javaClass.simpleName, "onCameraMove")
    }

    private fun setMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        viewModel.isReadyCheck(
            lat = getCurrentLocation()?.latitude ?: 0.0,
            lng = getCurrentLocation()?.longitude ?: 0.0
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