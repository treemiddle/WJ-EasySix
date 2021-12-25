package com.example.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.common.LabelType
import com.example.local.model.LabelEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface LabelDao {

    @Insert(onConflict = REPLACE)
    fun insertLabel(label: LabelEntity): Completable

    @Update
    fun updateLabel(label: LabelEntity): Completable

    @Query("SELECT * FROM label WHERE latitude =:latitude AND longitude =:longitude")
    fun getLabel(latitude: Double, longitude: Double): Single<LabelEntity>

    @Delete
    fun deleteAll(): Completable

}