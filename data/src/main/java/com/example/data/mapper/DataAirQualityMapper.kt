package com.example.data.mapper

import com.example.data.model.airquality.DataAirQuality
import com.example.domain.model.airquality.DomainAirQuality

object DataAirQualityMapper : WJDataMapper<DataAirQuality, DomainAirQuality> {

    override fun mapToData(from: DomainAirQuality): DataAirQuality {
        return DataAirQuality(
            aqi = from.aqi,
            name = from.name
        )
    }

    override fun mapToDomain(from: DataAirQuality): DomainAirQuality {
        return DomainAirQuality(
            aqi = from.aqi,
            name = from.name
        )
    }

}