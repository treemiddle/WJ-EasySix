package com.example.sample.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.usecase.AirQualityUseCase
import com.example.domain.usecase.BigDataUseCase
import com.example.domain.usecase.UserUseCase
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.AirQualityMapper
import com.example.sample.ui.mapper.BigDataMapper
import com.example.sample.ui.model.airquality.AirQuality
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.*
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
    private val bigDataUseCase: BigDataUseCase,
    private val userUseCase: UserUseCase,
) : BaseViewModel() {

    private val _isMapReady: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _isLocationReady: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _locationSubject: BehaviorSubject<Pair<Double, Double>> = BehaviorSubject.create()
    private val _markerButtonSubject: Subject<Unit> = PublishSubject.create()
    private val _aLocationSubejct: Subject<Unit> = PublishSubject.create()
    private val _bLocationSubejct: Subject<Unit> = PublishSubject.create()
    private var aqiDisposable: Disposable? = null
    private var bigDataDisposable: Disposable? = null

    private val _moveCamera = MutableLiveData<Pair<Double, Double>>()
    val moveCamera: LiveData<Pair<Double, Double>>
        get() = _moveCamera

    private val _airQualityModel = MutableLiveData<AirQuality>()
    val airQualityModel: LiveData<AirQuality>
        get() = _airQualityModel

    private val _locationA = MutableLiveData<String>()
    val locationA: LiveData<String>
        get() = _locationA

    private val _locationB = MutableLiveData<String>()
    val locationB: LiveData<String>
        get() = _locationB

    private val _markerButtonType = MutableLiveData(MarkerButtonType.NON_SELECTED)
    val markerButtonType: LiveData<MarkerButtonType>
        get() = _markerButtonType

    private val _currentTextType = MutableLiveData(CurrentTextType.V_TEXT)
    val currentTextType: LiveData<CurrentTextType>
        get() = _currentTextType

    private val _mapLabelClick = MutableLiveData<Event<MapLabelClick>>()
    val mapLabelClick: LiveData<Event<MapLabelClick>>
        get() = _mapLabelClick

    private val _labelA = MutableLiveData<PresentModel>()
    val labelA: LiveData<PresentModel>
        get() = _labelA

    private val _labelB = MutableLiveData<PresentModel>()
    val labelB: LiveData<PresentModel>
        get() = _labelB

    private val _labelSet = MutableLiveData<Pair<PresentModel, PresentModel>>()
    val labelSet: LiveData<Pair<PresentModel, PresentModel>>
        get() = _labelSet

    private var currentAqi = 0
    private var currentLatitude = 0.0
    private var currentLongitude = 0.0

    init {
        registerRx()
        setCurrentLanguage()
    }

    fun onMapReady(isReady: Boolean) {
        _isMapReady.onNext(isReady)
    }

    fun isReadyCheck(lat: Double, lng: Double) {
        _locationSubject.onNext(lat to lng)
        _isLocationReady.onNext(true)
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

    fun locationOnClickA() {
        _aLocationSubejct.onNext(Unit)
    }

    fun locationOnClickB() {
        _bLocationSubejct.onNext(Unit)
    }

    fun setUpdateLabel(item: PresentModel) {

    }

    private fun checkLocationTextA() {
        if (getLocationTextA().isNullOrEmpty().not()) {
            moveScreen(MapLabelClick.LABEL_A)
        }
    }

    private fun checkLocationTextB() {
        if (getLocationTextB().isNullOrEmpty().not()) {
            moveScreen(MapLabelClick.LABEL_B)
        }
    }

    private fun moveScreen(clickType: MapLabelClick) {
        _mapLabelClick.value = Event(clickType)
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

    private fun setCurrentLanguage() {
        userUseCase.setLanguage(userUseCase.getCurrentLanguage())
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

    private fun getCurrentLocation() {
        bigDataDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        currentAqi = getAqi()
        currentLatitude = getLocationSubject().first
        currentLongitude = getLocationSubject().second

        bigDataDisposable = bigDataUseCase.getLocationInfo(
            latitude = currentLatitude,
            longitude = currentLongitude,
            language = userUseCase.getLanguage()
        )
            .map(BigDataMapper::mapToPresentation)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }
            .subscribe({
                setCurrentText(it.locationName)
            }, { t ->
                makeLog(javaClass.simpleName, "getLocationInfo fail: ${t.localizedMessage}")
            }).addTo(compositeDisposable)
    }

    private fun setAirQuality(model: AirQuality) {
        _airQualityModel.value = model
    }

    private fun getAqi(): Int {
        return _airQualityModel.value?.aqi ?: 0
    }

    private fun getCurrentTextType(): CurrentTextType? {
        return _currentTextType.value
    }

    private fun getLocationTextA(): String? {
        return _locationA.value
    }

    private fun getLocationTextB(): String? {
        return _locationB.value
    }

    private fun checkCurrentText() {
        when (getCurrentTextType()) {
            CurrentTextType.BOOK_TEXT -> {
                setBothLabel()
                moveScreen(MapLabelClick.BOOK)
            }
            else -> {
                getCurrentLocation()
            }
        }
    }

    fun getLabelA(): PresentModel {
        return _labelA.value ?: PresentModel()
    }

    fun getLabelB(): PresentModel {
        return _labelB.value ?: PresentModel()
    }

    fun getBothLabel(): Pair<PresentModel, PresentModel> {
        return _labelSet.value ?: getLabelA() to getLabelB()
    }

    private fun setBothLabel() {
        _labelSet.value = getLabelA() to getLabelB()
    }

    private fun setLabel(type: LabelType, locationName: String): PresentModel {
        return PresentModel(
            type = type,
            aqi = currentAqi,
            latitude = currentLatitude,
            longitude = currentLongitude,
            locationName = locationName
        )
    }

    private fun setCurrentText(locationName: String) {
        when (getCurrentTextType()) {
            CurrentTextType.V_TEXT -> {
                setLocationA(locationName)
                setMarkerButtonType(MarkerButtonType.AREA_B_SELECTED)
                setLocationTextType(CurrentTextType.SET_B_TEXT)
                _labelA.value = setLabel(LabelType.A, locationName)
                makeLog(javaClass.simpleName, "a: ${_labelA.value}")
            }
            CurrentTextType.SET_B_TEXT -> {
                setLocationB(locationName)
                setMarkerButtonType(MarkerButtonType.BOTH_SELECTED)
                setLocationTextType(CurrentTextType.BOOK_TEXT)
                setVisibleOrInVisible(false)
                _labelB.value = setLabel(LabelType.B, locationName)
                makeLog(javaClass.simpleName, "b: ${_labelB.value}")
            }
            else -> {
                makeLog(javaClass.simpleName, "nothing...일어날 수가 없음...")
            }
        }
    }

    private fun setMarkerButtonType(type: MarkerButtonType) {
        _markerButtonType.value = type
    }

    private fun setLocationTextType(type: CurrentTextType) {
        _currentTextType.value = type
    }

    fun setLocationA(text: String) {
        _locationA.value = text
    }

    fun setLocationB(text: String) {
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

            _markerButtonSubject.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkCurrentText() },

            _aLocationSubejct.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkLocationTextA() },

            _bLocationSubejct.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkLocationTextB() }
        )
    }

}