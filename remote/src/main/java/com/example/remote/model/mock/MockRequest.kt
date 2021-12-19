package com.example.remote.model.mock

import com.example.remote.model.WJRemoteLayerData


data class RemoteMockRequest(
    val labelA: RemoteMockItem,
    val labelB: RemoteMockItem
) : WJRemoteLayerData

data class RemoteMockResponse(
    val labelA: RemoteMockItem,
    val labelB: RemoteMockItem,
    val price: Double
) : WJRemoteLayerData

data class RemoteMockItem(
    val labelType: String,
    val latitude: Double,
    val longitude: Double,
    val aqi: Int,
    val name: String
)