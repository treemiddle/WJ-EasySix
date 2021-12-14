package com.example.remote.model.mock

import com.example.remote.model.WJRemoteLayerData

data class MockResponse(
    val title: List<MockItem>
) : WJRemoteLayerData

data class MockItem(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)