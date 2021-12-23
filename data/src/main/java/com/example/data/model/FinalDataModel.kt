package com.example.data.model

data class FinalDataModel(
    val id: Long,
    val type: String,
    val aqi: Int,
    val latitude: Double,
    val longitude: Double,
    val nickname: String?,
    val locationName: String?
)