package com.example.data.remote

import com.example.data.model.mock.DataResponse
import io.reactivex.Single

interface MockRemoteDataSource {

    fun getAllLabel(labels: DataResponse): Single<DataResponse>

}