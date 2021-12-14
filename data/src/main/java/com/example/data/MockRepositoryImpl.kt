package com.example.data

import com.example.data.mapper.DataMockMapper
import com.example.data.remote.MockRemoteDataSource
import com.example.domain.model.mock.DomainMockResponse
import com.example.domain.repository.MockRepository
import io.reactivex.Single
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(private val mockRemoteDataSource: MockRemoteDataSource) : MockRepository {

    override fun getAllLabels(body: DomainMockResponse): Single<DomainMockResponse> {
        return mockRemoteDataSource.getAllLabel(DataMockMapper.mapToData(body))
            .map(DataMockMapper::mapToDomain)
    }

}