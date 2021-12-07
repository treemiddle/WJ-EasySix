package com.example.remote.mapper

import com.example.data.model.WJDataLayerData
import com.example.remote.model.WJRemoteLayerData

interface WJRemoteMapper<R : WJRemoteLayerData, D : WJDataLayerData> {

    fun mapToRemote(from: D): R

    fun mapToData(from: R): D

}