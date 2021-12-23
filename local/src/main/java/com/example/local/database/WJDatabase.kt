package com.example.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.dao.LabelDao
import com.example.local.model.LabelEntity

@Database(entities = [LabelEntity::class], exportSchema = false, version = 1)
abstract class WJDatabase : RoomDatabase() {
    abstract fun labelDao(): LabelDao
}