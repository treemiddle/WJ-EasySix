package com.example.remote.mapper

import com.example.data.model.mock.DataMockItem
import com.example.data.model.mock.DataResponse
import com.example.remote.model.mock.MockItem
import com.example.remote.model.mock.MockResponse

object RemoteMockMapper : WJRemoteMapper<MockResponse, DataResponse> {

    override fun mapToRemote(from: DataResponse): MockResponse {
        return MockResponse(
            title = from.title.map {
                MockItem(
                    labelType = it.labelType,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    aqi = it.aqi,
                    name = it.name
                )
            }
        )
    }

    override fun mapToData(from: MockResponse): DataResponse {
        return DataResponse(
            title = from.title.map {
                DataMockItem(
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
