package com.example.sample.utils.bindingadatper

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setLocationA")
fun bindLocationA(tv: TextView, nickname: String?) {
    if (nickname.isNullOrEmpty()) {
        tv.text = null
        tv.hint = "A"
    } else {
        tv.hint = null
        tv.text = nickname
    }
}

@BindingAdapter("setLocationB")
fun bindLocationB(tv: TextView, nickname: String?) {
    if (nickname.isNullOrEmpty()) {
        tv.text = null
        tv.hint = "B"
    } else {
        tv.hint = null
        tv.text = nickname
    }
}