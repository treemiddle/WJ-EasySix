package com.example.sample.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.usecase.MockUseCase
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.mapToDomain
import com.example.sample.ui.mapper.mapToPresentation
import com.example.sample.ui.model.mock.MockData
import com.example.sample.ui.model.mock.MockResponse
import com.example.sample.utils.makeLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val mockUseCase: MockUseCase
) : BaseViewModel() {

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String>
        get() = _totalPrice

    private val _totalCount = MutableLiveData<String>()
    val totalCount: LiveData<String>
        get() = _totalCount

    private val _labelList = MutableLiveData<List<MockData>>()
    val labelList: LiveData<List<MockData>>
        get() = _labelList

    fun getHistory() {
        mockUseCase.getHistory(2021, 12)
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.mapToPresentation() }
            .subscribe({
                test(it)
                setTotalCalculate(it)
            }, { t ->
                makeLog(javaClass.simpleName, "fail: ${t.localizedMessage}")
            }).addTo(compositeDisposable)
    }

    private fun test(data: List<MockResponse>) {
        val list = mutableListOf<MockData>()
        val labelA = data.map { it.labelA }
        val labelB = data.map { it.labelB }
        labelA.map { list.add(it) }
        labelB.map { list.add(it) }

        _labelList.value = list
    }

    private fun setTotalCalculate(data: List<MockResponse>) {
        val domain = data.mapToDomain()

        _totalCount.value = mockUseCase.getTotalCount(domain).toString()
        _totalPrice.value = mockUseCase.getTotalPrice(domain).toString()
    }

}