package com.example.data.remote

import com.example.data.model.mock.DataRequest
import com.example.data.model.mock.DataResponse
import io.reactivex.Single

interface MockRemoteDataSource {

    fun getAllLabel(labels: DataRequest): Single<DataResponse>

    fun getHistory(year: Int, month: Int): Single<List<DataResponse>>

}