package com.example.data

import com.example.data.mapper.DataBigDataMapper
import com.example.data.remote.BigDataRemoteDataSource
import com.example.domain.model.bigdata.DomainBigData
import com.example.domain.repository.BigDataRepository
import io.reactivex.Single
import javax.inject.Inject

class BigDataRepositoryImpl @Inject constructor(
    private val bigDataRemoteDataSource: BigDataRemoteDataSource
) : BigDataRepository {

    override fun getLocationInfo(latitude: Double, longitude: Double): Single<DomainBigData> {
        return bigDataRemoteDataSource.getLocationInfo(latitude, longitude)
            .map(DataBigDataMapper::mapToDomain)
    }

}