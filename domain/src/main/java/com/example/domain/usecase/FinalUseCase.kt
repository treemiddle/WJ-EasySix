package com.example.domain.usecase

import com.example.domain.model.FinalDomainModel
import com.example.domain.repository.FinalRepository
import io.reactivex.Single
import javax.inject.Inject

class FinalUseCase @Inject constructor(private val repository: FinalRepository) {

    fun getAirQuality(lat: Double, lng: Double): Single<FinalDomainModel> {
        return repository.getAirQuality(lat, lng)
    }

    fun getLocationInfo(
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<FinalDomainModel> {
        return repository.getLocationInfo(aqi, latitude, longitude, language)
    }

}