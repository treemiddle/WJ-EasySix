package com.example.sample.ui.mapper

import com.example.domain.model.airquality.DomainAirQuality
import com.example.sample.ui.model.airquality.AirQuality

object AirQualityMapper : WJPresentationMapper<DomainAirQuality, AirQuality> {

    override fun mapToPresentation(from: DomainAirQuality): AirQuality {
        return AirQuality(
            aqi = from.aqi,
            name = from.name
        )
    }

    override fun mapToDomain(from: AirQuality): DomainAirQuality {
        return DomainAirQuality(
            aqi = from.aqi,
            name = from.name
        )
    }

}