package com.example.data.mapper

import com.example.data.model.mock.DataMockItem
import com.example.data.model.mock.DataRequest
import com.example.data.model.mock.DataResponse
import com.example.domain.model.mock.DomainMockItem
import com.example.domain.model.mock.DomainResponse
import com.example.domain.model.mock.DomainRequest

fun DomainRequest.mapToData(): DataRequest {
    return DataRequest(
        labelA = DataMockItem(
            labelType = labelA.labelType,
            latitude = labelA.latitude,
            longitude = labelA.longitude,
            aqi = labelA.aqi,
            name = labelA.name
        ),
        labelB = DataMockItem(
            labelType = labelB.labelType,
            latitude = labelB.latitude,
            longitude = labelB.longitude,
            aqi = labelB.aqi,
            name = labelB.name
        )
    )
}

fun DataResponse.mapToDomain(): DomainResponse {
    return DomainResponse(
        labelA = DomainMockItem(
            labelType = labelA.labelType,
            latitude = labelA.latitude,
            longitude = labelA.longitude,
            aqi = labelA.aqi,
            name = labelA.name
        ),
        labelB = DomainMockItem(
            labelType = labelB.labelType,
            latitude = labelB.latitude,
            longitude = labelB.longitude,
            aqi = labelB.aqi,
            name = labelB.name
        ),
        price = price
    )
}

fun List<DataResponse>.mapToDomain(): List<DomainResponse> {
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