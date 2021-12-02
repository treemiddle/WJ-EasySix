package com.example.data

import com.example.data.mapper.DataAirQualityMapper
import com.example.data.remote.AirQualityRemoteDataSource
import com.example.domain.model.airquality.DomainAirQuality
import com.example.domain.repository.AirQualityRepository
import io.reactivex.Single
import javax.inject.Inject

class AirQualityRepositoryImpl @Inject constructor(
    private val airQualityRemoteDataSource: AirQualityRemoteDataSource,
) : AirQualityRepository {

    override fun getAirQuality(lat: Double, lng: Double): Single<DomainAirQuality> {
        return airQualityRemoteDataSource.getAirQuality(lat, lng)
            .map(DataAirQualityMapper::mapToDomain)
    }

}