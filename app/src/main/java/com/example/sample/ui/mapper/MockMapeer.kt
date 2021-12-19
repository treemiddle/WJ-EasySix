package com.example.sample.ui.mapper

import com.example.domain.model.mock.DomainMockItem
import com.example.domain.model.mock.DomainRequest
import com.example.domain.model.mock.DomainResponse
import com.example.sample.ui.model.mock.MockData
import com.example.sample.ui.model.mock.MockRequest
import com.example.sample.ui.model.mock.MockResponse
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.labelTypeToString

fun PresentModel.mapToMock(): DomainMockItem {
    return DomainMockItem(
        labelType = labelTypeToString(type),
        latitude = latitude,
        longitude = longitude,
        aqi = aqi,
        name = locationName ?: ""
    )
}

fun DomainResponse.mapToPresentation(): MockResponse {
    return MockResponse(
        labelA = MockData(
            labelType = labelA.labelType,
            latitude = labelA.latitude,
            longitude = labelA.longitude,
            aqi = labelA.aqi,
            name = labelA.name
        ),
        labelB = MockData(
            labelType = labelB.labelType,
            latitude = labelB.latitude,
            longitude = labelB.longitude,
            aqi = labelB.aqi,
            name = labelB.name
        ),
        price = price
    )
}