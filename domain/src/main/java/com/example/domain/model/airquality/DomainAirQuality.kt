package com.example.domain.model.airquality

import com.example.domain.model.WJDomainLayerData

data class DomainAirQuality(
    val aqi: Int,
    val name: String
) : WJDomainLayerData