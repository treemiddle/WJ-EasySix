package com.example.sample.utils.bindingadatper

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("setVisibleOrInVisible")
fun View.bindVisibleOrInVisible(state: Boolean) {
    visibility = if (state) View.VISIBLE else View.INVISIBLE
}