package com.example.remote.di

import com.example.remote.api.MockApi
import com.example.remote.mapper.requestToResponse
import com.example.remote.model.mock.RemoteMockRequest
import com.example.remote.model.mock.RemoteMockResponse
import io.reactivex.Single

class MockApiImpl : MockApi {

    override fun getAllLabel(requestBody: RemoteMockRequest): Single<RemoteMockResponse> {
        return Single.just(requestBody)
            .map { it.requestToResponse() }
    }

}