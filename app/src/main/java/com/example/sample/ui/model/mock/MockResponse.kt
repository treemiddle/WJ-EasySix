package com.example.sample.ui.model.mock

import com.example.sample.ui.model.WJPresentationData

data class MockRequest(
    val labelA: MockData,
    val labelB: MockData,
) : WJPresentationData

data class MockResponse(
    val labelA: MockData,
    val labelB: MockData,
    val price: Double
) : WJPresentationData

data class MockData(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)

data class Test(
    val label: List<MockData>
)