package com.example.remote.mapper

import com.example.data.model.airquality.DataAirQuality
import com.example.remote.model.airquality.AirQualityResponse

object RemoteAirQualityMapper : WJRemoteMapper<AirQualityResponse, DataAirQuality> {

    override fun mapToRemote(from: DataAirQuality): AirQualityResponse {
        TODO("Not yet implemented")
    }

    override fun mapToData(from: AirQualityResponse): DataAirQuality {
        return DataAirQuality(
            aqi = from.data.aqi,
            name = from.data.city.name
        )
    }

}