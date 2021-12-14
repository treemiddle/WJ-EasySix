package com.example.domain.model.mock

import com.example.domain.model.WJDomainLayerData

data class DomainMockResponse(
    val title: List<DomainMockItem>
) : WJDomainLayerData

data class DomainMockItem(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)