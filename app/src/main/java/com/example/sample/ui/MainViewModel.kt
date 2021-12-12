package com.example.sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sample.base.BaseViewModel
import com.example.sample.utils.Event
import com.example.sample.utils.MapLabelClick
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _mapLabelClick = MutableLiveData<Event<MapLabelClick>>()
    val mapLabelClick: LiveData<Event<MapLabelClick>>
        get() = _mapLabelClick

    fun moveScreen(mapLabelClickType: MapLabelClick) {
        _mapLabelClick.value = Event(mapLabelClickType)
    }

}