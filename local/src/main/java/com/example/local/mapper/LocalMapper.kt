package com.example.local.mapper

import com.example.data.model.FinalDataModel
import com.example.local.model.LabelEntity

fun LabelEntity.toData(): FinalDataModel {
    return FinalDataModel(
        id = id,
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}

fun FinalDataModel.toLocal(): LabelEntity {
    return LabelEntity(
        id = id,
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}