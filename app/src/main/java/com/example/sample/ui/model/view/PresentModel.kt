package com.example.sample.ui.model.view

import com.example.common.LabelType

data class PresentModel(
    val id: Long = 0,
    var type: LabelType = LabelType.A,
    var aqi: Int = 0,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var nickname: String? = null,
    var locationName: String? = null
)
