package com.example.sample.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.mock.DomainRequest
import com.example.domain.repository.MockRepository
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.mapToMock
import com.example.sample.ui.mapper.mapToPresentation
import com.example.sample.ui.model.mock.MockData
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.DEFAULT_THROTTLE_DURATION
import com.example.sample.utils.makeLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val mockRepository: MockRepository) :
    BaseViewModel() {

    private val _nextClickSubject = PublishSubject.create<Unit>()

    private val _mockList = MutableLiveData<List<MockData>>()
    val mockList: LiveData<List<MockData>>
        get() = _mockList

    private val _price = MutableLiveData<String>()
    val price: LiveData<String>
        get() = _price

    private val _next = MutableLiveData<Unit>()
    val next: LiveData<Unit>
        get() = _next

    init {
        regesiterRx()
    }

    fun onNextClick() {
        _nextClickSubject.onNext(Unit)
    }

    fun getBooks(item: Pair<PresentModel, PresentModel>) {
        mockRepository.getAllLabels(
            DomainRequest(
                labelA = item.first.mapToMock(),
                labelB = item.second.mapToMock()
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.mapToPresentation() }
            .subscribe({
                setMockList(it.labelA, it.labelB)
                setPrice(it.price)
            }, {
                makeLog(javaClass.simpleName, "fail: ${it.localizedMessage}")
            }).addTo(compositeDisposable)
    }

    private fun setMockList(labelA: MockData, labelB: MockData) {
        val list = arrayListOf<MockData>().apply {
            add(labelA)
            add(labelB)
        }

        _mockList.value = list
    }

    private fun setPrice(price: Double) {
        _price.value = price.toString()
    }

    private fun setNext() {
        _next.value = Unit
    }

    private fun regesiterRx() {
        compositeDisposable.addAll(
            _nextClickSubject.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setNext() }
        )
    }

}