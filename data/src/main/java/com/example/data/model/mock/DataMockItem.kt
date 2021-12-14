package com.example.data.model.mock

import com.example.data.model.WJDataLayerData

data class DataResponse(
    val title: List<DataMockItem>
) : WJDataLayerData

data class DataMockItem(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)