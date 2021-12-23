package com.example.data.remote

import com.example.data.model.FinalDataModel
import com.example.data.model.airquality.DataAirQuality
import com.example.data.model.bigdata.DataBigData
import io.reactivex.Single

interface FinalRemoteDataSource {

    fun getAirQuality(lat: Double, lng: Double): Single<FinalDataModel>

    fun getLocationInfo(
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String
    ): Single<FinalDataModel>
}