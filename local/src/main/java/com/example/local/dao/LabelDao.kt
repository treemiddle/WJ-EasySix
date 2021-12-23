package com.example.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.local.model.LabelEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LabelDao {

    @Insert(onConflict = REPLACE)
    fun insertLabel(label: LabelEntity): Completable

    @Update
    fun updateLabel(label: LabelEntity)

    @Query("SELECT * FROM label WHERE latitude =:latitude AND longitude =:longitude")
    fun getLabel(latitude: Double, longitude: Double): Single<LabelEntity>

}