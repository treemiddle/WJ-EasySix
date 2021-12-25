package com.example.data.model

import com.example.common.LabelType

data class FinalDataModel(
    val id: Long = 0,
    val type: LabelType? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val nickname: String? = null,
    val locationName: String? = null,
    val aqi: Int = 0
)