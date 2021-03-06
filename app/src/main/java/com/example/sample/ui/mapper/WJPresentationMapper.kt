package com.example.sample.ui.mapper

import com.example.domain.model.WJDomainLayerData
import com.example.sample.ui.model.WJPresentationData

interface WJPresentationMapper<D : WJDomainLayerData, P : WJPresentationData> {

    fun mapToPresentation(from: D): P

    fun mapToDomain(from: P): D

}