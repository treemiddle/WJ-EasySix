package com.example.remote.mapper

import com.example.data.model.bigdata.DataBigData
import com.example.remote.model.bigdata.Location

object RemoteBigDataMapper : WJRemoteMapper<Location, DataBigData> {

    override fun mapToRemote(from: DataBigData): Location {
        return Location(locationName = from.locationName)
    }

    override fun mapToData(from: Location): DataBigData {
        return DataBigData(locationName = from.locationName)
    }

}