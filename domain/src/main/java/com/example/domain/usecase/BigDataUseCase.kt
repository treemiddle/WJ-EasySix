package com.example.domain.usecase

import com.example.domain.model.bigdata.DomainBigData
import com.example.domain.repository.BigDataRepository
import io.reactivex.Single
import javax.inject.Inject

class BigDataUseCase @Inject constructor(private val bigDataRepository: BigDataRepository) {

    private val locationList = mutableListOf<DomainBigData>()

    fun getLocationInfo(
        latitude: Double,
        longitude: Double,
        language: String
    ): Single<DomainBigData> {
        return bigDataRepository.getLocationInfo(latitude, longitude, language)
    }

    fun addLocation(model: DomainBigData) {
        locationList.add(model)
    }

    fun getLocationFirstItem(): DomainBigData? {
        return if (locationList.isEmpty()) {
            null
        } else {
            locationList[0]
        }
    }

    fun getLocationLastItem(): DomainBigData? {
        return if (locationList.isEmpty()) {
            null
        } else {
            locationList[locationList.lastIndex]
        }
    }

}