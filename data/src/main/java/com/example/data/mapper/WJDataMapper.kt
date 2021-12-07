package com.example.data.mapper

import com.example.data.model.WJDataLayerData
import com.example.domain.model.WJDomainLayerData

interface WJDataMapper<D : WJDataLayerData, Domain : WJDomainLayerData> {

    fun mapToData(from: Domain): D

    fun mapToDomain(from: D): Domain

}