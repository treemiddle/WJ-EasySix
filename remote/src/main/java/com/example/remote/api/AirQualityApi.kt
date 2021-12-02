package com.example.remote.api

import com.example.remote.BuildConfig
import com.example.remote.model.airquality.AirQualityResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AirQualityApi {

    @GET("feed/geo:{lat};{lng}/?token=${BuildConfig.AIR_QUALITY_TOKEN}")
    fun getAirQuality(
        @Path("lat") lat: Double,
        @Path("lng") lng: Double
    ): Single<AirQualityResponse>

}