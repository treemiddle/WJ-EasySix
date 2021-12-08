package com.example.remote.api

import com.example.remote.model.bigdata.BigDataRespons
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BigDataApi {

    @GET("data/reverse-geocode-client")
    fun getLocationInfo(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("localityLanguage") localityLanguage: String
    ): Single<BigDataRespons>

}