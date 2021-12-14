package com.example.sample.ui.model.mock

import com.example.sample.ui.model.WJPresentationData

data class MockResponse(
    val title: List<MockData>
) : WJPresentationData

data class MockData(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)