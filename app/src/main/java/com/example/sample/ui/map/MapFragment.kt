package com.example.sample.ui.map

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remote.AirQualityRemoteDataSourceImpl
import com.example.sample.R
import com.example.sample.base.BaseFragment
import com.example.sample.databinding.FragmentMapBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

/**
 * 공기 퀄리티 값 aqi
 * 위치 정보 city -> lat, lng, name
 */
@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
    }

    override fun initData() {

    }

    override fun bindViews() {
        //val mapFragment = activity?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment
//        map = binding.map
//        map?.getMapAsync(this)
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //map = googleMap
        val aaaa = LatLng(37.654601, 127.060530)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(aaaa))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))

    }

    override fun onStart() {
        super.onStart()
       // map?.onStart()
    }

    override fun onResume() {
        super.onResume()
        //map?.onResume()
    }



}