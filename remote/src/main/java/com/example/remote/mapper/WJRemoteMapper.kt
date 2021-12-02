package com.example.remote.mapper

import com.example.data.model.airquality.WJDataLayerData
import com.example.remote.model.airquality.WJRemoteLayerData

interface WJRemoteMapper<R : WJRemoteLayerData, D : WJDataLayerData> {

    fun mapToRemote(from: D): R

    fun mapToData(from: R): D

}