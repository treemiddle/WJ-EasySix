package com.example.sample.base

import com.example.sample.utils.MapLabelClick

interface AppNavigator {

    fun initMain()
    fun setMain()
    fun screenTo(screen: MapLabelClick)

}