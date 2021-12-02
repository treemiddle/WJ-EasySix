package com.example.data.remote

import com.example.data.model.airquality.DataAirQuality
import io.reactivex.Single

interface AirQualityRemoteDataSource {

    fun getAirQuality(lat: Double, lng: Double): Single<DataAirQuality>

}