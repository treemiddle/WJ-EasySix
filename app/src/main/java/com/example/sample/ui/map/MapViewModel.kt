package com.example.sample.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.usecase.AirQualityUseCase
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.AirQualityMapper
import com.example.sample.ui.model.airquality.AirQuality
import com.example.sample.utils.MarkerButtonType
import com.example.sample.utils.makeLog
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val airQualityUseCase: AirQualityUseCase
): BaseViewModel() {

    private val _isMapReady: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _isLocationReady: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _locationSubject: BehaviorSubject<Pair<Double, Double>> = BehaviorSubject.create()
    private val _markerButtonSubject: Subject<Unit> = PublishSubject.create()
    private var disposable: Disposable? = null

    private val _moveCamera = MutableLiveData<Pair<Double, Double>>()
    val moveCamera: LiveData<Pair<Double, Double>>
        get() = _moveCamera

    private val _airQualityModel = MutableLiveData<AirQuality>()
    val airQualityModel: LiveData<AirQuality>
        get() = _airQualityModel

    private val _markerButtonType = MutableLiveData(MarkerButtonType.NON_SELECTED)
    val markerButtonType: LiveData<MarkerButtonType>
        get() = _markerButtonType

    init {
        registerRx()
    }

    fun onMapReady(isReady: Boolean) {
        _isMapReady.onNext(isReady)
    }

    fun isReadyCheck(lat: Double, lng: Double) {
        _isLocationReady.onNext(true)
        _locationSubject.onNext(lat to lng)
    }

    fun checkCurrentLocation(lat: Double? = null, lng: Double? = null) {
        val latLng = getLocationSubject()

        if (lat == null && lng == null) {
            getAqi(latLng.first, latLng.second)
        } else {
            //todo 통신안할려고 잠시 막아둠
            //getAqi(lat!!, lng!!)
        }
    }

    fun setMarkerButtonClick() {
        _markerButtonSubject.onNext(Unit)
    }

    private fun setMoveCamera() {
        _moveCamera.value = getLocationSubject()
    }

    private fun getLocationSubject(): Pair<Double, Double> {
        return _locationSubject.value ?: 0.0 to 0.0
    }

    private fun mapReadyObservable(): Observable<Boolean> {
        return _isMapReady.filter { isChecked -> isChecked }
    }

    private fun locationReadyObservable(): Observable<Boolean> {
        return _isLocationReady.filter { isChecked -> isChecked }
    }

    private fun getAqi(lat: Double, lng: Double) {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        disposable = airQualityUseCase.getAirQuality(lat, lng)
            .map(AirQualityMapper::mapToPresentation)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }
            .subscribe({
                bindAirQuality(it)
            }, { t ->
                makeLog(javaClass.simpleName, "getCurrentQuality fail: ${t.localizedMessage}")
            })
            .addTo(compositeDisposable)
    }

    private fun bindAirQuality(model: AirQuality) {
        _airQualityModel.value = model
    }

    private fun setMarkerButtonType() {
        when (_markerButtonType.value) {
            MarkerButtonType.NON_SELECTED -> {
                makeLog(javaClass.simpleName, "button type none")
                _markerButtonType.value = MarkerButtonType.AREA_B_SELECTED
            }
            MarkerButtonType.AREA_A_SELECTED -> {
                makeLog(javaClass.simpleName, "button type a: 두번째 화면으로")
            }
            MarkerButtonType.AREA_B_SELECTED -> {
                makeLog(javaClass.simpleName, "button type b: 두번째 화면으로")
                _markerButtonType.value = MarkerButtonType.BOTH_SELECTED
            }
            MarkerButtonType.BOTH_SELECTED -> {
                makeLog(javaClass.simpleName, "button type both: 세번째 화면으로")
            }
        }
    }

    private fun registerRx() {
        compositeDisposable.addAll(
            Observable.combineLatest(
                mapReadyObservable(),
                locationReadyObservable(),
                { map: Boolean, location: Boolean -> Pair(map, location) }
            )
                .map { (m, l) -> m && l }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setMoveCamera(); checkCurrentLocation() },

            _markerButtonSubject.throttleFirst(750L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setMarkerButtonType() }
        )
    }

}