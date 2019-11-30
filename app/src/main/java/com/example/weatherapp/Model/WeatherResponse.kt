package com.example.weatherapp.Model

import kotlinx.serialization.Serializable


@Serializable
data class WeatherResponse(
    val consolidated_weather: ArrayList<Consolidated_Weather>?,
    val title: String? ="",
    val woeid: Int? =0
)