package com.example.sample.ui.mapper

import com.example.common.LabelType
import com.example.common.labelTypeToString
import com.example.common.stringToLabelType
import com.example.data.model.FinalDataModel
import com.example.domain.model.FinalDomainModel
import com.example.sample.ui.model.view.PresentModel

fun PresentModel.mapToDomain(): FinalDomainModel {
    return FinalDomainModel(
        id = id,
        type = type,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}

fun FinalDomainModel.mapToPresentation(): PresentModel {
    return PresentModel(
        id = id,
        type = type ?: LabelType.A,
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}