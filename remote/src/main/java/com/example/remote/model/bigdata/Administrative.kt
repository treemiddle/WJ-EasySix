package com.example.remote.model.bigdata

import com.example.remote.model.WJRemoteLayerData

data class Administrative(
    val adminLevel: Int,
    val description: String,
    val geonameId: Int,
    val isoCode: String,
    val isoName: String,
    val name: String,
    val order: Int,
    val wikidataId: String
)

data class Location(
    val aqi: Int = 0,
    val locationName: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : WJRemoteLayerData