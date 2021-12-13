package com.example.sample.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.DEFAULT_THROTTLE_DURATION
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor() : BaseViewModel() {

    private val _saveBtnSubject = PublishSubject.create<Unit>()

    private val _checkNickName = MutableLiveData<Unit>()
    val checkNickName: LiveData<Unit>
        get() = _checkNickName

    private val _labelModel = MutableLiveData<PresentModel>()
    val labelModel: LiveData<PresentModel>
        get() = _labelModel

    private val _updateLabelModel = MutableLiveData<PresentModel>()
    val updateLabelModel: LiveData<PresentModel>
        get() = _updateLabelModel

    val name = MutableLiveData<String?>()

    init {
        registerRx()
    }

    fun onSaveButtonClicked() {
        _saveBtnSubject.onNext(Unit)
    }

    fun setLabelModel(item: PresentModel) {
        _labelModel.value = item
        setEditText(item.nickname)
    }

    private fun setEditText(nickname: String?) {
        name.value = nickname
    }

    private fun checkedNickName() {
        val currentNickName = name.value

        if (!currentNickName.isNullOrEmpty()) {
            setNickName(currentNickName)
        }

        setCheckNickName()
    }

    private fun getLabelModel(): PresentModel? {
        return _labelModel.value
    }

    private fun setNickName(nickname: String) {
        getLabelModel()?.let {
            _updateLabelModel.value = it.apply {
                this.nickname = nickname
            }
        }
    }

    private fun setCheckNickName() {
        _checkNickName.value = Unit
    }

    private fun registerRx() {
        compositeDisposable.addAll(
            _saveBtnSubject.throttleFirst(DEFAULT_THROTTLE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checkedNickName() }
        )
    }

}