package com.example.remote.mapper

import com.example.data.model.FinalDataModel
import com.example.remote.model.airquality.AirQualityResponse
import com.example.remote.model.bigdata.Location

fun AirQualityResponse.mapToData(): FinalDataModel {
    return FinalDataModel(aqi = data.aqi)
}

fun Location.mapToData(): FinalDataModel {
    return FinalDataModel(
        aqi = aqi,
        type = type,
        locationName = locationName,
        latitude = latitude,
        longitude = longitude
    )
}