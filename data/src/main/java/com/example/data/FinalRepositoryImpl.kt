package com.example.data

import com.example.common.LabelType
import com.example.data.ext.floor3
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
        type: LabelType,
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDomainModel> {
        return localDataSource.getLabel(latitude.floor3(), longitude.floor3())
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                FinalDataModel(
                    type = type,
                    aqi = aqi,
                    latitude = latitude,
                    longitude = longitude,
                )
            }
            .flatMap { cachedLabel ->
                if (cachedLabel.locationName.isNullOrEmpty()) {
                    getRemoteLabel(type, aqi, latitude, longitude, language)
                        .map { it }
                } else {
                    Single.just(cachedLabel.mapToDomain())
                }
            }
    }

    private fun getRemoteLabel(
        type: LabelType,
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDomainModel> {
        return remoteDataSource.getLocationInfo(type, aqi, latitude, longitude, language)
            .flatMap { remoteLabel ->
                val newLabel = remoteLabel.copy(latitude = latitude.floor3(), longitude = longitude.floor3())

                localDataSource.insertLabel(newLabel)
                    .andThen(Single.just(remoteLabel.mapToDomain()))
            }
    }

}