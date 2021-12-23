package com.example.local.mapper

import com.example.data.model.FinalDataModel
import com.example.local.model.LabelEntity

fun LabelEntity.mapToModel(): FinalDataModel {
    return FinalDataModel(
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}

fun FinalDataModel.mapToData(): LabelEntity {
    return LabelEntity(
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}