package com.example.remote


import com.example.data.model.mock.DataResponse
import com.example.data.remote.MockRemoteDataSource
import com.example.remote.api.MockApi
import com.example.remote.mapper.RemoteMockMapper
import io.reactivex.Single
import javax.inject.Inject

class MockRemoteDataSourceImpl @Inject constructor(private val mockApi: MockApi) :
    MockRemoteDataSource {

    override fun getAllLabel(labels: DataResponse): Single<DataResponse> {
        return mockApi.getAllLabel(RemoteMockMapper.mapToRemote(labels))
            .map(RemoteMockMapper::mapToData)
    }

}