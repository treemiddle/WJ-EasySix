package com.example.sample.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.usecase.AirQualityUseCase
import com.example.domain.usecase.BigDataUseCase
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.AirQualityMapper
import com.example.sample.ui.mapper.BigDataMapper
import com.example.sample.ui.model.airquality.AirQuality
import com.example.sample.ui.model.bigdata.BigData
import com.example.sample.utils.LocationTextType
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
    private val airQualityUseCase: AirQualityUseCase,
    private val bigDataUseCase: BigDataUseCase
): BaseViewModel() {

    private val _isMapReady: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _isLocationReady: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _locationSubject: BehaviorSubject<Pair<Double, Double>> = BehaviorSubject.create()
    private val _markerButtonSubject: Subject<Unit> = PublishSubject.create()
    private var aqiDisposable: Disposable? = null
    private var bigDataDisposable: Disposable? = null

    private val _moveCamera = MutableLiveData<Pair<Double, Double>>()
    val moveCamera: LiveData<Pair<Double, Double>>
        get() = _moveCamera

    private val _airQualityModel = MutableLiveData<AirQuality>()
    val airQualityModel: LiveData<AirQuality>
        get() = _airQualityModel

    private val _bigDataModel = MutableLiveData<BigData>()
    val bigDataModel: LiveData<BigData>
        get() = _bigDataModel

    private val _locationA = MutableLiveData<String>()
    val locationA: LiveData<String>
        get() = _locationA

    private val _locationB = MutableLiveData<String>()
    val locationB: LiveData<String>
        get() = _locationB

    private val _markerButtonType = MutableLiveData(MarkerButtonType.NON_SELECTED)
    val markerButtonType: LiveData<MarkerButtonType>
        get() = _markerButtonType

    private val _locationTextType = MutableLiveData(LocationTextType.EMPTY)
    val locationTextType: LiveData<LocationTextType>
        get() = _locationTextType

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
        val earlyLocation = getLocationSubject()

        if (lat == null && lng == null) {
            getAqi(earlyLocation.first, earlyLocation.second)
        } else {
            getAqi(lat!!, lng!!)
            _locationSubject.onNext(lat to lng)
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

    private fun getBigDta(): String {
        return _bigDataModel.value?.locationName ?: ""
    }

    private fun mapReadyObservable(): Observable<Boolean> {
        return _isMapReady.filter { isChecked -> isChecked }
    }

    private fun locationReadyObservable(): Observable<Boolean> {
        return _isLocationReady.filter { isChecked -> isChecked }
    }

    private fun getAqi(lat: Double, lng: Double) {
        aqiDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        aqiDisposable = airQualityUseCase.getAirQuality(lat, lng)
            .map(AirQualityMapper::mapToPresentation)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }
            .subscribe({
                setAirQuality(it)
            }, { t ->
                makeLog(javaClass.simpleName, "getCurrentQuality fail: ${t.localizedMessage}")
            })
            .addTo(compositeDisposable)
    }

    private fun getLocationInfo() {
        bigDataDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        bigDataDisposable = bigDataUseCase.getLocationInfo(getLocationSubject().first, getLocationSubject().second)
            .map(BigDataMapper::mapToPresentation)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }
            .subscribe({
                setBigData(it)
                quaterLocationTextType()
            }, { t ->
                makeLog(javaClass.simpleName, "getLocationInfo fail: ${t.localizedMessage}")
            }).addTo(compositeDisposable)
    }

    private fun setAirQuality(model: AirQuality) {
        _airQualityModel.value = model
    }

    private fun quaterLocationTextType() {
        when (_locationTextType.value) {
            LocationTextType.EMPTY -> {
                setLocationA(getBigDta())
                setMarkerButtonType(MarkerButtonType.AREA_B_SELECTED)
                setLocationTextType(LocationTextType.LOCATION_A)
            }
            LocationTextType.LOCATION_A -> {
                setLocationB(getBigDta())
                setMarkerButtonType(MarkerButtonType.BOTH_SELECTED)
                setLocationTextType(LocationTextType.LOCATION_B)
            }
            LocationTextType.LOCATION_B -> {
                // something
            }
        }
    }

    private fun setMarkerButtonType(type: MarkerButtonType) {
        _markerButtonType.value = type
    }

    private fun setLocationTextType(type: LocationTextType) {
        _locationTextType.value = type
    }

    private fun setBigData(model: BigData) {
        _bigDataModel.value = model
    }

    private fun setLocationA(text: String) {
        _locationA.value = text
    }

    private fun setLocationB(text: String) {
        _locationB.value = text
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
                .subscribe { getLocationInfo() }
        )
    }

}