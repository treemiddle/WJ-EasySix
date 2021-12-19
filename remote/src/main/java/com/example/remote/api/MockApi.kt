package com.example.remote.api

import com.example.remote.model.mock.RemoteMockRequest
import com.example.remote.model.mock.RemoteMockResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface MockApi {

    @POST("/book")
    fun getAllLabel(@Body requestBody: RemoteMockRequest): Single<RemoteMockResponse>

}