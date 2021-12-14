package com.example.domain.repository

import com.example.domain.model.mock.DomainMockResponse
import io.reactivex.Single

interface MockRepository {

    fun getAllLabels(body: DomainMockResponse): Single<DomainMockResponse>
}