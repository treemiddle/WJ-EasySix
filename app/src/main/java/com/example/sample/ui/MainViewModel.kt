package com.example.sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sample.base.BaseViewModel
import com.example.sample.ui.model.view.PresentModel
import com.example.sample.utils.Event
import com.example.sample.utils.MapLabelClick
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _mapLabelClick = MutableLiveData<Event<MapLabelClick>>()
    val mapLabelClick: LiveData<Event<MapLabelClick>>
        get() = _mapLabelClick

    private val _labelAorB = MutableLiveData<PresentModel>()
    val labelAorB: LiveData<PresentModel>
        get() = _labelAorB

    private val _inMemoryLabel = MutableLiveData<PresentModel>()
    val inMemoryLabel: LiveData<PresentModel>
        get() = _inMemoryLabel

    private val _labelSet = MutableLiveData<Pair<PresentModel, PresentModel>>()
    val labelSet: LiveData<Pair<PresentModel, PresentModel>>
        get() = _labelSet

    private val _reset = MutableLiveData<Unit>()
    val reset: LiveData<Unit>
        get() = _reset

    private val _history = MutableLiveData<Unit>()
    val history: LiveData<Unit>
        get() = _history

    fun setLabelAorB(item: PresentModel) {
        _labelAorB.value = item
    }

    fun moveScreen(mapLabelClickType: MapLabelClick) {
        _mapLabelClick.value = Event(mapLabelClickType)
    }

    fun setInMemoryLabel(item: PresentModel) {
        _inMemoryLabel.value = item
    }

    fun setBothLabel(item: Pair<PresentModel, PresentModel>) {
        _labelSet.value = item
    }

    fun reset() {
        _reset.value = Unit
    }

    fun openHistory() {
        _history.value = Unit
    }

}