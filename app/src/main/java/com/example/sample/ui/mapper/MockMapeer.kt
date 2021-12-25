package com.example.sample.ui.mapper

import com.example.common.labelTypeToString
import com.example.domain.model.mock.DomainMockItem
import com.example.domain.model.mock.DomainRequest
import com.example.domain.model.mock.DomainResponse
import com.example.sample.ui.model.mock.MockData
import com.example.sample.ui.model.mock.MockRequest
import com.example.sample.ui.model.mock.MockResponse
import com.example.sample.ui.model.mock.Test
import com.example.sample.ui.model.view.PresentModel

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

fun List<DomainResponse>.mapToPresentation(): List<MockResponse> {
    return this.map {
        MockResponse(
            labelA = MockData(
                labelType = it.labelA.labelType,
                latitude = it.labelA.latitude,
                longitude = it.labelA.longitude,
                aqi = it.labelA.aqi,
                name = it.labelA.name
            ),
            labelB = MockData(
                labelType = it.labelB.labelType,
                latitude = it.labelB.latitude,
                longitude = it.labelB.longitude,
                aqi = it.labelB.aqi,
                name = it.labelB.name
            ),
            price = it.price
        )
    }
}

fun List<MockResponse>.mapToDomain(): List<DomainResponse> {
    return this.map {
        DomainResponse(
            labelA = DomainMockItem(
                labelType = it.labelA.labelType,
                latitude = it.labelA.latitude,
                longitude = it.labelA.longitude,
                aqi = it.labelA.aqi,
                name = it.labelA.name
            ),
            labelB = DomainMockItem(
                labelType = it.labelB.labelType,
                latitude = it.labelB.latitude,
                longitude = it.labelB.longitude,
                aqi = it.labelB.aqi,
                name = it.labelB.name
            ),
            price = it.price
        )
    }
}