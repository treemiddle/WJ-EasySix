package com.example.sample.ui.book

import com.example.domain.model.mock.DomainMockItem
import com.example.domain.model.mock.DomainMockResponse
import com.example.domain.repository.MockRepository
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.MockMapeer
import com.example.sample.utils.makeLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val mockRepository: MockRepository) :
    BaseViewModel() {

    fun test() {
        mockRepository.getAllLabels(
            DomainMockResponse(listOf(
                DomainMockItem("1", 1.0, 1.0, 1, "1231sf"),
                DomainMockItem("2", 3.0, 5.5, 165, "bbbbasdf")
            ))
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(MockMapeer::mapToPresentation)
            .subscribe({
                makeLog(javaClass.simpleName, "ohmygod: $it")
            }, {
                makeLog(javaClass.simpleName, "fail: ${it.localizedMessage}")
            }).addTo(compositeDisposable)
    }

}