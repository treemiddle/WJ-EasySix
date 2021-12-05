package com.example.sample.ui

import com.example.domain.usecase.AirQualityUseCase
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.AirQualityMapper
import com.example.sample.utils.makeLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val airQualityUseCase: AirQualityUseCase
) : BaseViewModel() {


    init {
//        airQualityUseCase.getAirQuality(5.0, 13.7)
//            .map(AirQualityMapper::mapToPresentation)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                makeLog(javaClass.simpleName, "success: $it")
//            }, {
//                makeLog(javaClass.simpleName, it.localizedMessage)
//            }).addTo(compositeDisposable)
    }

}