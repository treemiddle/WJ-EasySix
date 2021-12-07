package com.example.domain.usecase

import com.example.domain.model.bigdata.DomainBigData
import com.example.domain.repository.BigDataRepository
import io.reactivex.Single
import javax.inject.Inject

class BigDataUseCase @Inject constructor(private val bigDataRepository: BigDataRepository) {

    fun getLocationInfo(latitude: Double, longitude: Double): Single<DomainBigData> {
        return bigDataRepository.getLocationInfo(latitude, longitude)
    }

}