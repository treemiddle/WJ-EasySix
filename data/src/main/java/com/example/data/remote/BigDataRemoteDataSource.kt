package com.example.data.remote

import com.example.data.model.bigdata.DataBigData
import io.reactivex.Single

interface BigDataRemoteDataSource {

    fun getLocationInfo(
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<DataBigData>

}