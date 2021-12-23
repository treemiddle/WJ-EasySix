package com.example.data

import com.example.data.local.FinalLocalSource
import com.example.data.mapper.DataAirQualityMapper
import com.example.data.mapper.mapToData
import com.example.data.mapper.mapToDomain
import com.example.data.model.FinalDataModel
import com.example.data.remote.FinalRemoteDataSource
import com.example.domain.model.FinalDomainModel
import com.example.domain.model.airquality.DomainAirQuality
import com.example.domain.model.bigdata.DomainBigData
import com.example.domain.repository.FinalRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class FinalRepositoryImpl @Inject constructor(
    private val remoteDataSource: FinalRemoteDataSource,
    private val localDataSource: FinalLocalSource,
) : FinalRepository {

    override fun getAirQuality(lat: Double, lng: Double): Single<FinalDomainModel> {
        return remoteDataSource.getAirQuality(lat, lng)
            .map { it.mapToDomain() }
    }

    override fun getLocationInfo(
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDomainModel> {
        return localDataSource.getLabel(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                FinalDataModel(
                    aqi = aqi,
                    latitude = latitude,
                    longitude = longitude
                )
            }
            .flatMap { cachedLabel ->
                if (cachedLabel.locationName.isNullOrEmpty()) {
                    getRemoteLabel(aqi, latitude, longitude, language)
                        .map { it }
                } else {
                    Single.just(cachedLabel.mapToDomain())
                }
            }
    }

    private fun getRemoteLabel(
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDomainModel> {
        return remoteDataSource.getLocationInfo(aqi, latitude, longitude, language)
            .flatMap { remoteLabel ->
                localDataSource.insertLabel(remoteLabel)
                    .andThen(Single.just(remoteLabel.mapToDomain()))
            }
    }


}