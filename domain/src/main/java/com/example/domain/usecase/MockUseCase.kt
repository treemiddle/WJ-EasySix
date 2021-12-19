package com.example.domain.usecase

import com.example.domain.model.mock.DomainResponse
import com.example.domain.model.mock.DomainRequest
import com.example.domain.repository.MockRepository
import io.reactivex.Single
import javax.inject.Inject

class MockUseCase @Inject constructor(private val repository: MockRepository) {

    fun getAllLabels(body: DomainRequest): Single<DomainResponse> {
        return repository.getAllLabels(body)
    }

    fun getHistory(year: Int, month: Int): Single<List<DomainResponse>> {
        return repository.getHistory(year, month)
    }

    fun getTotalCount(itemList: List<DomainResponse>): Int {
        return itemList.size * 2
    }

    fun getTotalPrice(itemList: List<DomainResponse>): Double {
        return itemList.map { it.price }.sum()
    }

}