package com.example.domain.model

data class FinalDomainModel(
    val type: String? = null,
    val aqi: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val nickname: String? = null,
    val locationName: String? = null
) : WJDomainLayerData