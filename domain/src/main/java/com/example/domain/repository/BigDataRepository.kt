package com.example.domain.repository

import com.example.domain.model.bigdata.DomainBigData
import io.reactivex.Single

interface BigDataRepository {

    fun getLocationInfo(latitude: Double, longitude: Double): Single<DomainBigData>
}