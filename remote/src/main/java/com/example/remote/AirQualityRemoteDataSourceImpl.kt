package com.example.remote

import com.example.data.model.airquality.DataAirQuality
import com.example.data.remote.AirQualityRemoteDataSource
import com.example.remote.api.AirQualityApi
import com.example.remote.mapper.RemoteAirQualityMapper
import io.reactivex.Single
import javax.inject.Inject

class AirQualityRemoteDataSourceImpl @Inject constructor(private val airQualityApi: AirQualityApi) : AirQualityRemoteDataSource {

    override fun getAirQuality(lat: Double, lng: Double): Single<DataAirQuality> {
        return airQualityApi.getAirQuality(lat, lng)
            .map(RemoteAirQualityMapper::mapToData)
    }

}