package com.example.remote

import com.example.common.LabelType
import com.example.data.model.FinalDataModel
import com.example.data.remote.FinalRemoteDataSource
import com.example.remote.api.AirQualityApi
import com.example.remote.api.BigDataApi
import com.example.remote.mapper.mapToData
import com.example.remote.model.bigdata.Location
import io.reactivex.Single
import javax.inject.Inject

class FinalRemoteDataSourceImpl @Inject constructor(
    private val airQualityApi: AirQualityApi,
    private val locationApi: BigDataApi
) : FinalRemoteDataSource {

    override fun getAirQuality(lat: Double, lng: Double): Single<FinalDataModel> {
        return airQualityApi.getAirQuality(lat, lng)
            .map { it.mapToData() }
    }

    override fun getLocationInfo(
        type: LabelType,
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDataModel> {
        return locationApi.getLocationInfo(latitude, longitude, language)
            .flatMap { orderSort ->
                val orderList = orderSort.localityInfo.administrative.sortedBy { it.order }.reversed()
                Single.just(
                    Location(
                        type = type,
                        aqi = aqi,
                        locationName = "${orderList[1].name}, ${orderList[0].name}",
                        latitude = latitude,
                        longitude = longitude
                    )
                )
            }
            .map { it.mapToData() }
    }
}