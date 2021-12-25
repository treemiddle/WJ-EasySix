package com.example.domain.usecase

import com.example.common.LabelType
import com.example.domain.model.FinalDomainModel
import com.example.domain.repository.FinalRepository
import io.reactivex.Single
import javax.inject.Inject

class FinalUseCase @Inject constructor(private val repository: FinalRepository) {

    fun getAirQuality(lat: Double, lng: Double): Single<FinalDomainModel> {
        return repository.getAirQuality(lat, lng)
    }

    fun getLocationInfo(
        type: LabelType,
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDomainModel> {
        return repository.getLocationInfo(type, aqi, latitude, longitude, language)
    }

}