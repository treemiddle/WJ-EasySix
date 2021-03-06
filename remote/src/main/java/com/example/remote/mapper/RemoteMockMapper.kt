package com.example.remote.mapper

import com.example.data.model.mock.DataMockItem
import com.example.data.model.mock.DataRequest
import com.example.data.model.mock.DataResponse
import com.example.remote.model.mock.RemoteMockItem
import com.example.remote.model.mock.RemoteMockRequest
import com.example.remote.model.mock.RemoteMockResponse

fun RemoteMockResponse.mapToData(): DataResponse {
    return DataResponse(
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
        ),
        price = 1234.5678
    )
}

fun RemoteMockRequest.requestToResponse(): RemoteMockResponse {
    return RemoteMockResponse(
        labelA = RemoteMockItem(
            labelType = labelA.labelType,
            latitude = labelA.latitude,
            longitude = labelA.longitude,
            aqi = labelA.aqi,
            name = labelA.name
        ),
        labelB = RemoteMockItem(
            labelType = labelB.labelType,
            latitude = labelB.latitude,
            longitude = labelB.longitude,
            aqi = labelB.aqi,
            name = labelB.name
        ),
        price = 1234.5678
    )
}

fun DataRequest.mapToRemoteRequest(): RemoteMockRequest {
    return RemoteMockRequest(
        labelA = RemoteMockItem(
            labelType = labelA.labelType,
            latitude = labelA.latitude,
            longitude = labelA.longitude,
            aqi = labelA.aqi,
            name = labelA.name
        ),
        labelB = RemoteMockItem(
            labelType = labelB.labelType,
            latitude = labelB.latitude,
            longitude = labelB.longitude,
            aqi = labelB.aqi,
            name = labelB.name
        )
    )
}

fun List<RemoteMockResponse>.mapToData(): List<DataResponse> {
    return this.map {
        DataResponse(
            labelA = DataMockItem(
                labelType = it.labelA.labelType,
                latitude = it.labelA.latitude,
                longitude = it.labelA.longitude,
                aqi = it.labelA.aqi,
                name = it.labelA.name
            ),
            labelB = DataMockItem(
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