package com.example.weatherapp.Controllers

import com.example.weatherapp.Model.WeatherResponse
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import org.jetbrains.anko.doAsync
import java.net.URL
import javax.security.auth.callback.Callback


@ImplicitReflectionSerializer
class APIController {
    fun APIController() = Unit
    @UnstableDefault
    fun getForecastForCity(woeid: Int, callback: (WeatherResponse) -> Unit) {
        doAsync {
            val apiResponse = URL("https://www.metaweather.com/api/location/" + woeid).readText()
            //println(Json.nonstrict.parse(WeatherResponse.serializer(),apiResponse))
            val result = Json.nonstrict.parse(WeatherResponse.serializer(), apiResponse)
            callback(result)
        }

    }
}