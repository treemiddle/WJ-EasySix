package com.example.domain.repository

import com.example.domain.model.airquality.DomainAirQuality
import io.reactivex.Single

interface AirQualityRepository {

    fun getAirQuality(lat: Double, lng: Double): Single<DomainAirQuality>

}