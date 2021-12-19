package com.example.domain.repository

import com.example.domain.model.mock.DomainResponse
import com.example.domain.model.mock.DomainRequest
import io.reactivex.Single

interface MockRepository {

    fun getAllLabels(body: DomainRequest): Single<DomainResponse>

    fun getHistory(year: Int, month: Int): Single<List<DomainResponse>>
}