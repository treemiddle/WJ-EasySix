package com.example.sample.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.LabelType
import com.example.data.mapper.mapToData
import com.example.domain.model.FinalDomainModel
import com.example.domain.usecase.AirQualityUseCase
import com.example.domain.usecase.BigDataUseCase
import com.example.domain.usecase.FinalUseCase
import com.example.domain.usecase.UserUseCase
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.mapper.AirQualityMapper
import com.example.sample.ui.mapper.BigDataMapper
import com.example.sample.ui.mapper.mapToDomain
import com.example.sample.ui.mapper.mapToPresentation
import com.example.sample.ui.model.airquality.AirQuality
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
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
    private val finalUseCase: FinalUseCase
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

    private val _nicknameA = MutableLiveData<String?>()
    val nicknameA: LiveData<String?>
        get() = _nicknameA

    private val _nicknameB = MutableLiveData<String?>()
    val nicknameB: LiveData<String?>
        get() = _nicknameB

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

    private val _labelType = MutableLiveData<LabelType?>()
    val labelType: LiveData<LabelType?>
        get() = _labelType

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

    fun updateNicknameLabelA(nickname: String) {
        _nicknameA.value = nickname
        makeLog(javaClass.simpleName, "A 닉네임 받았어요: $nickname")
        getLabelA().also { it.nickname = nickname }
        makeLog(javaClass.simpleName, "닉넴 업뎃: ${getLabelA()}")
        finalUseCase.updateLabel(getLabelA().mapToDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                makeLog(javaClass.simpleName, "업로드 성공")
            }, { t ->
                makeLog(javaClass.simpleName, "fail: ${t.localizedMessage}")
            }).addTo(compositeDisposable)
    }

    fun updateNicknameLabelB(nickname: String) {
        _nicknameB.value = nickname
        getLabelB().also { it.nickname = nickname }
        makeLog(javaClass.simpleName, "닉넴 업뎃: ${getLabelB()}")
        finalUseCase.updateLabel(getLabelB().mapToDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                makeLog(javaClass.simpleName, "업로드 성공")
                getCurrentLocation()

            }, { t ->
                makeLog(javaClass.simpleName, "fail: ${t.localizedMessage}")
            }).addTo(compositeDisposable)
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

    private fun getLabelType(): LabelType {
        return _labelType.value ?: LabelType.A
    }

    private fun setLabelType(type: LabelType?) {
        _labelType.value = type
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

        bigDataDisposable = finalUseCase.getLocationInfo(
            type = getLabelType(),
            aqi = currentAqi,
            latitude = currentLatitude,
            longitude = currentLongitude,
            language = userUseCase.getLanguage()
        )
            .map { it.mapToPresentation() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doAfterTerminate { hideLoading() }
            .subscribe({
                //setCurrentText(it.locationName!!)
                setData(it)
                makeLog(javaClass.simpleName, "시발!!!: $it")
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

    fun reset() {
        setLabelType(null)
        _nicknameA.value = null
        _nicknameB.value = null
        setMarkerButtonType(MarkerButtonType.NON_SELECTED)
        _currentTextType.value = CurrentTextType.V_TEXT
        setVisibleOrInVisible(true)
    }

    private fun setBothLabel() {
        _labelSet.value = getLabelA() to getLabelB()
    }

    private fun setLabel(type: LabelType, model: PresentModel) {
        when (type) {
            LabelType.A -> _labelA.value = model
            LabelType.B -> _labelB.value = model
        }
    }

    private fun setData(model: PresentModel) {
        when (getCurrentTextType()) {
            CurrentTextType.V_TEXT -> {
                _nicknameA.value = model.locationName
                setLabel(getLabelType(), model)
                setLabelType(LabelType.B)
                setMarkerButtonType(MarkerButtonType.AREA_B_SELECTED)
                setLocationTextType(CurrentTextType.SET_B_TEXT)
                makeLog(javaClass.simpleName, "a: ${_labelA.value}")
            }
            CurrentTextType.SET_B_TEXT -> {
                _nicknameB.value = model.locationName
                setLabel(getLabelType(), model)
                setMarkerButtonType(MarkerButtonType.BOTH_SELECTED)
                setLocationTextType(CurrentTextType.BOOK_TEXT)
                setVisibleOrInVisible(false)
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

    private fun checkLocationTextA() {
        if (_nicknameA.value.isNullOrEmpty().not()) {
            moveScreen(MapLabelClick.LABEL_A)
        }
    }

    private fun checkLocationTextB() {
        if (_nicknameB.value.isNullOrEmpty().not()) {
            moveScreen(MapLabelClick.LABEL_B)
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

            _markerButtonSubject.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkCurrentText() },

            // A 로케이션 클릭 시 값 넘겨서 화면 이동
            _aLocationSubejct.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkLocationTextA() },

             // B 로케이션 클릭 시 값 넘겨서 화면 이동
            _bLocationSubejct.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkLocationTextB() }
        )
    }

}