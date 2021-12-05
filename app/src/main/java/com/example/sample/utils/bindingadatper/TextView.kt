package com.example.sample.utils.bindingadatper

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.sample.R
import com.example.sample.utils.MarkerButtonType
import com.example.sample.utils.makeLog

@BindingAdapter("setCurrentMarkerButton")
fun TextView.bindCurrentMarkerButton(markerType: MarkerButtonType) {
    text = when (markerType) {
        MarkerButtonType.NON_SELECTED -> { context.getString(R.string.map_button_text_01) }
        MarkerButtonType.AREA_A_SELECTED -> { context.getString(R.string.map_button_text_03) }
        MarkerButtonType.AREA_B_SELECTED -> { context.getString(R.string.map_button_text_02) }
        MarkerButtonType.BOTH_SELECTED -> { context.getString(R.string.map_button_text_04) }
    }
}