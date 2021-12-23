package com.example.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.local.model.LabelEntity

@Dao
interface LabelDao {

    @Insert
    fun insertLabel(label: LabelEntity)

}