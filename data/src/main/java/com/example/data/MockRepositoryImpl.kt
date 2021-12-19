package com.example.data

import com.example.data.mapper.mapToData
import com.example.data.mapper.mapToDomain
import com.example.data.remote.MockRemoteDataSource
import com.example.domain.model.mock.DomainResponse
import com.example.domain.model.mock.DomainRequest
import com.example.domain.repository.MockRepository
import io.reactivex.Single
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(private val mockRemoteDataSource: MockRemoteDataSource) : MockRepository {

    override fun getAllLabels(body: DomainRequest): Single<DomainResponse> {
        return mockRemoteDataSource.getAllLabel(body.mapToData())
            .map { it.mapToDomain() }
    }

    override fun getHistory(year: Int, month: Int): Single<List<DomainResponse>> {
        return mockRemoteDataSource.getHistory(year, month)
            .map { it.mapToDomain() }
    }

}