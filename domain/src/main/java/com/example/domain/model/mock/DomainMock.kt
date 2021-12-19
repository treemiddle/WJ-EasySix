package com.example.domain.model.mock

import com.example.domain.model.WJDomainLayerData

data class DomainRequest(
    val labelA: DomainMockItem,
    val labelB: DomainMockItem
) : WJDomainLayerData

data class DomainResponse(
    val labelA: DomainMockItem,
    val labelB: DomainMockItem,
    val price: Double
) : WJDomainLayerData

data class DomainMockItem(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)