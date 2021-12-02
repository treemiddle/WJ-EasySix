package com.example.sample.ui

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.AirQualityUseCase
import com.example.sample.ui.mapper.AirQualityMapper
import com.example.sample.utils.makeLog
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val airQualityUseCase: AirQualityUseCase
) : ViewModel() {

    private val compositeDisposable by lazy(::CompositeDisposable)

    init {
        airQualityUseCase.getAirQuality(5.0, 13.7)
            .map(AirQualityMapper::mapToPresentation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                makeLog(javaClass.simpleName, "success: $it")
            }, {
                makeLog(javaClass.simpleName, it.localizedMessage)
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}