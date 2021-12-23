package com.example.local.convert

import androidx.room.TypeConverter
import com.example.common.LabelType

object LocalConvert {

    @TypeConverter
    fun fromLabelType(type: LabelType?): String? {
        return type?.name
    }

    @TypeConverter
    fun toLabelType(type: String?): LabelType {
        return if (type.isNullOrEmpty()) {
            LabelType.A
        } else {
            LabelType.valueOf(type)
        }
    }

}