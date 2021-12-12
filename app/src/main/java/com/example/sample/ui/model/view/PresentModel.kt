package com.example.sample.ui.model.view

import com.example.sample.utils.LabelType

data class PresentModel(
    var type: LabelType = LabelType.A,
    var aqi: Int = 0,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var nickname: String? = null,
    var locationName: String? = null
)
