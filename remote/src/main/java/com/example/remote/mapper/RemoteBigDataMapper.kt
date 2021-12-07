package com.example.remote.mapper

import com.example.data.model.bigdata.DataBigData
import com.example.remote.model.bigdata.BigDataRespons

object RemoteBigDataMapper : WJRemoteMapper<BigDataRespons, DataBigData> {

    override fun mapToRemote(from: DataBigData): BigDataRespons {
        TODO("Not yet implemented")
    }

    override fun mapToData(from: BigDataRespons): DataBigData {
        return DataBigData(locationName = from.locality)
    }

}