package com.example.data.mapper

import com.example.data.model.bigdata.DataBigData
import com.example.domain.model.bigdata.DomainBigData

object DataBigDataMapper : WJDataMapper<DataBigData, DomainBigData> {

    override fun mapToData(from: DomainBigData): DataBigData {
        return DataBigData(locationName = from.locationName)
    }

    override fun mapToDomain(from: DataBigData): DomainBigData {
        return DomainBigData(locationName = from.locationName)
    }

}