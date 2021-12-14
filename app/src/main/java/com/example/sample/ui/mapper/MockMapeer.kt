package com.example.sample.ui.mapper

import com.example.domain.model.mock.DomainMockItem
import com.example.domain.model.mock.DomainMockResponse
import com.example.sample.ui.model.mock.MockData
import com.example.sample.ui.model.mock.MockResponse

object MockMapeer : WJPresentationMapper<DomainMockResponse, MockResponse> {

    override fun mapToPresentation(from: DomainMockResponse): MockResponse {
        return MockResponse(
            title = from.title.map {
                MockData(
                    labelType = it.labelType,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    aqi = it.aqi,
                    name = it.name
                )
            }
        )
    }

    override fun mapToDomain(from: MockResponse): DomainMockResponse {
        return DomainMockResponse(
            title = from.title.map {
                DomainMockItem(
                    labelType = it.labelType,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    aqi = it.aqi,
                    name = it.name
                )
            }
        )
    }
}