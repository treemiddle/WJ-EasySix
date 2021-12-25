package com.example.local.model

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.common.LabelType

@DatabaseView
@Entity(tableName = "label")
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: LabelType?,
    val aqi: Int,
    val latitude: Double,
    val longitude: Double,
    var nickname: String?,
    val locationName: String?
)