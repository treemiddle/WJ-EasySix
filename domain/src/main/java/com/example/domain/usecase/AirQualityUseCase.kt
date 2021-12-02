package com.example.domain.usecase

import com.example.domain.model.airquality.DomainAirQuality
import com.example.domain.repository.AirQualityRepository
import io.reactivex.Single
import javax.inject.Inject

class AirQualityUseCase @Inject constructor(private val airQualityRepository: AirQualityRepository) {

    fun getAirQuality(lat: Double, lng: Double): Single<DomainAirQuality> {
        return airQualityRepository.getAirQuality(lat, lng)
    }

}