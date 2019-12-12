package com.example.weatherapp.Model

import kotlinx.serialization.Serializable


@Serializable
data class CityGuesses(
    val woeid: Int,
    val title: String? = ""
)