package com.example.domain.model

import com.example.common.LabelType

data class FinalDomainModel(
    val type: LabelType? = null,
    val aqi: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val nickname: String? = null,
    val locationName: String? = null
) : WJDomainLayerData