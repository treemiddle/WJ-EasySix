package com.example.remote.model.airquality

import com.example.remote.model.WJRemoteLayerData

data class AirQualityResponse(
    val data: Data,
    val status: String
) : WJRemoteLayerData