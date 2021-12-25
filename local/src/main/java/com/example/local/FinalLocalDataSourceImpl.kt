package com.example.local

import com.example.common.LabelType
import com.example.data.local.FinalLocalSource
import com.example.data.model.FinalDataModel
import com.example.local.dao.LabelDao
import com.example.local.mapper.mapToData
import com.example.local.mapper.mapToModel
import com.example.local.model.LabelEntity
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableFromSingle
import javax.inject.Inject

class FinalLocalDataSourceImpl @Inject constructor(
    private val labelDao: LabelDao
) : FinalLocalSource {

    override fun insertLabel(model: FinalDataModel): Completable {
        return labelDao.insertLabel(model.mapToData())
    }

    override fun getLabel(lat: Double, lng: Double): Single<FinalDataModel> {
        return labelDao.getLabel(lat, lng)
            .map { it.mapToModel() }
    }

    override fun findLabel(model: FinalDataModel): Single<FinalDataModel> {
        val newModel = model.mapToData()

        return labelDao.getLabel(newModel.latitude, newModel.longitude)
            .map { label -> label.copy(nickname = newModel.nickname) }
            .flatMap {
                labelDao.updateLabel(it)
                    .andThen(Single.just(it.mapToModel()))
            }
    }

    override fun deleteAll(): Completable {
        return labelDao.deleteAll()
    }
}