package com.example.sample.utils.bindingadatper

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter
fun View.bindVisibleOrInVisible(view: View, state: Boolean) {
    if (state) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}