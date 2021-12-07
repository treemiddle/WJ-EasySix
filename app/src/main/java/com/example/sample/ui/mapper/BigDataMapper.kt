package com.example.sample.ui.mapper

import com.example.domain.model.bigdata.DomainBigData
import com.example.sample.ui.model.bigdata.BigData

object BigDataMapper : WJPresentationMapper<DomainBigData, BigData> {

    override fun mapToPresentation(from: DomainBigData): BigData {
        return BigData(locationName = from.locationName)
    }

    override fun mapToDomain(from: BigData): DomainBigData {
        return DomainBigData(locationName = from.locationName)
    }

}