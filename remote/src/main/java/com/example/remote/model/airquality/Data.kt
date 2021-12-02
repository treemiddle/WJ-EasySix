package com.example.remote.model.airquality

data class Data(
    val aqi: Int,
    val attributions: List<Attribution>,
    val city: City,
    val debug: Debug,
    val dominentpol: String,
    val forecast: Forecast,
    val iaqi: Iaqi,
    val idx: Int,
    val time: Time
)