package com.example.sample.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable by lazy(::CompositeDisposable)
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isVisibleOrInvisible = MutableLiveData(true)
    val isVisibleOrInvisible: LiveData<Boolean>
        get() = _isVisibleOrInvisible

    protected fun showLoading() {
        _isLoading.value = true
    }

    protected fun hideLoading() {
        _isLoading.value = false
    }

    protected fun setVisibleOrInVisible(state: Boolean) {
        _isVisibleOrInvisible.value = state
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}