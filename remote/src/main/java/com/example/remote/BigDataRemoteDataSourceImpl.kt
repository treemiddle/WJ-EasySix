package com.example.remote

import com.example.data.model.bigdata.DataBigData
import com.example.data.remote.BigDataRemoteDataSource
import com.example.remote.api.BigDataApi
import com.example.remote.mapper.RemoteBigDataMapper
import io.reactivex.Single
import javax.inject.Inject

class BigDataRemoteDataSourceImpl @Inject constructor(private val bigDataApi: BigDataApi) : BigDataRemoteDataSource {

    override fun getLocationInfo(
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<DataBigData> {
        return bigDataApi.getLocationInfo(latitude, longitude, language)
            .map(RemoteBigDataMapper::mapToData)
    }

}