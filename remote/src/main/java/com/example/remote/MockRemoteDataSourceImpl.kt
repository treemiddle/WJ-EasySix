package com.example.remote


import com.example.data.model.mock.DataRequest
import com.example.data.model.mock.DataResponse
import com.example.data.remote.MockRemoteDataSource
import com.example.remote.api.MockApi
import com.example.remote.mapper.mapToData
import com.example.remote.mapper.mapToRemoteRequest
import io.reactivex.Single
import javax.inject.Inject

class MockRemoteDataSourceImpl @Inject constructor(private val mockApi: MockApi) :
    MockRemoteDataSource {

    override fun getAllLabel(labels: DataRequest): Single<DataResponse> {
        return mockApi.getAllLabel(labels.mapToRemoteRequest())
            .map { it.mapToData() }
    }

    override fun getHistory(year: Int, month: Int): Single<List<DataResponse>> {
        return mockApi.getHistory(year, month)
            .map { it.mapToData() }
    }

}