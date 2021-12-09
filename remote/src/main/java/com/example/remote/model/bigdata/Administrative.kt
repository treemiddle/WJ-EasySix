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
    val locationName: String
) : WJRemoteLayerData