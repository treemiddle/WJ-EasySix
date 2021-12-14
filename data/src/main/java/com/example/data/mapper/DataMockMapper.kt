package com.example.data.mapper

import com.example.data.model.mock.DataMockItem
import com.example.data.model.mock.DataResponse
import com.example.domain.model.mock.DomainMockItem
import com.example.domain.model.mock.DomainMockResponse

object DataMockMapper : WJDataMapper<DataResponse, DomainMockResponse> {

    override fun mapToData(from: DomainMockResponse): DataResponse {
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

    override fun mapToDomain(from: DataResponse): DomainMockResponse {
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