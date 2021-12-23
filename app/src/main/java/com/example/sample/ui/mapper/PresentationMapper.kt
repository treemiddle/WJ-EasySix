package com.example.sample.ui.mapper

import com.example.data.model.FinalDataModel
import com.example.domain.model.FinalDomainModel
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.labelTypeToString
import com.example.sample.utils.stringToLabelType

fun PresentModel.mapToDomain(): FinalDomainModel {
    return FinalDomainModel(
        type = labelTypeToString(type),
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}

fun FinalDomainModel.mapToPresentation(): PresentModel {
    return PresentModel(
        type = stringToLabelType(type),
        aqi = aqi,
        latitude = latitude,
        longitude = longitude,
        nickname = nickname,
        locationName = locationName
    )
}