package com.example.data.model.mock

import com.example.data.model.WJDataLayerData

data class DataRequest(
    val labelA: DataMockItem,
    val labelB: DataMockItem
) : WJDataLayerData

data class DataResponse(
    val labelA: DataMockItem,
    val labelB: DataMockItem,
    val price: Double,
) : WJDataLayerData

data class DataMockItem(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)