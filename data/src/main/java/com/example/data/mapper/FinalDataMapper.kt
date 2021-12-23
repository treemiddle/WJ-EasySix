package com.example.data.mapper

import com.example.data.model.FinalDataModel
import com.example.domain.model.FinalDomainModel

fun FinalDataModel.mapToDomain(): FinalDomainModel {
    return FinalDomainModel(
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}

fun FinalDomainModel.mapToData(): FinalDataModel {
    return FinalDataModel(
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}