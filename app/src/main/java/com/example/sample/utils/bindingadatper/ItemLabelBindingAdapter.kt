package com.example.sample.utils.bindingadatper

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setLabelType")
fun bindLabelType(tv: TextView, type: String) {
    tv.text = type
}

@BindingAdapter("setLabelLatitude")
fun bindLabelLatitude(tv: TextView, lat: Double) {
    tv.text = lat.toString()
}

@BindingAdapter("setLabelLongitude")
fun bindLabelLongitude(tv: TextView, lng: Double) {
    tv.text = lng.toString()
}

@BindingAdapter("setLabelAqi")
fun bindLabelAqi(tv:TextView, aqi: Int) {
    tv.text = aqi.toString()
}