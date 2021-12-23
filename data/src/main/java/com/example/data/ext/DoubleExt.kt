package com.example.data.ext

import kotlin.math.floor

fun Double.floor3(): Double {
    return floor(this * 1000) / 1000
}