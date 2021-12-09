package com.example.remote

import com.example.data.model.bigdata.DataBigData
import com.example.data.remote.BigDataRemoteDataSource
import com.example.remote.api.BigDataApi
import com.example.remote.mapper.RemoteBigDataMapper
import com.example.remote.model.bigdata.Administrative
import com.example.remote.model.bigdata.Location
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class BigDataRemoteDataSourceImpl @Inject constructor(private val bigDataApi: BigDataApi) : BigDataRemoteDataSource {

    override fun getLocationInfo(
        latitude: Double,
        longitude: Double,
        language: String,
    ): Single<DataBigData> {
        return bigDataApi.getLocationInfo(latitude, longitude, language)
            .flatMap { orderSort ->
                val orderList = orderSort.localityInfo.administrative.sortedBy { it.order }.reversed()
                Single.just(
                    Location(
                        locationName = "${orderList[1].name}, ${orderList[0].name}"
                    )
                )
            }
            .map(RemoteBigDataMapper::mapToData)
    }

}