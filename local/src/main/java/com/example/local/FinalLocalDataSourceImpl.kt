package com.example.local

import com.example.common.LabelType
import com.example.data.local.FinalLocalSource
import com.example.data.model.FinalDataModel
import com.example.local.dao.LabelDao
import com.example.local.mapper.mapToData
import com.example.local.mapper.mapToModel
import com.example.local.model.LabelEntity
import io.reactivex.Completable
import io.reactivex.Single
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

}