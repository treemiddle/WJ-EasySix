package com.example.remote.di

import com.example.remote.api.MockApi
import com.example.remote.model.mock.MockResponse
import io.reactivex.Single
import javax.inject.Inject

class MockApiImpl : MockApi {

    override fun getAllLabel(requestBody: MockResponse): Single<MockResponse> {
        return Single.just(requestBody)
    }

//    private fun provideResponse(requestBody: MockResponse): Response<MockResponse> {
//        return Response.success(200, requestBody)
//    }
//
//    private fun provideErrorBody(): ResponseBody {
//        return "{ message: 'invalid_information' }"
//            .toResponseBody("application/json".toMediaTypeOrNull())
//    }

}