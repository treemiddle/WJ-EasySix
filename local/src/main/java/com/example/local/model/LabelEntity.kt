package com.example.local.model

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@DatabaseView
@Entity(tableName = "label")
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val type: String?,
    val aqi: Int,
    val latitude: Double,
    val longitude: Double,
    val nickname: String?,
    val locationName: String?
)