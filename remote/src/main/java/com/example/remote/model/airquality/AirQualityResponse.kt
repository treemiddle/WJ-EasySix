package com.example.remote.model.airquality

data class AirQualityResponse(
    val data: Data,
    val status: String
) : WJRemoteLayerData