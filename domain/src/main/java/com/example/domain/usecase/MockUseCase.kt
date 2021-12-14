package com.example.domain.usecase

import com.example.domain.model.mock.DomainMockResponse
import com.example.domain.repository.MockRepository
import io.reactivex.Single
import javax.inject.Inject

class MockUseCase @Inject constructor(private val repository: MockRepository) {

    fun getAllLabels(body: DomainMockResponse): Single<DomainMockResponse> {
        return repository.getAllLabels(body)
    }

}