package com.example.domain.repository

import com.example.common.LabelType
import com.example.domain.model.FinalDomainModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface FinalRepository {

    fun getAirQuality(lat: Double, lng: Double): Single<FinalDomainModel>

    fun getLocationInfo(
        type: LabelType,
        aqi: Int,
        latitude: Double,
        longitude: Double,
        language: String
    ): Single<FinalDomainModel>

    fun updateLabel(label: FinalDomainModel): Completable

    fun deleteAll(): Completable
}