package com.example.data.local

import com.example.common.LabelType
import com.example.data.model.FinalDataModel
import com.example.domain.model.FinalDomainModel
import io.reactivex.Completable
import io.reactivex.Single

interface FinalLocalSource {

    fun insertLabel(model: FinalDataModel): Completable

    fun getLabel(lat: Double, lng: Double): Single<FinalDataModel>

}