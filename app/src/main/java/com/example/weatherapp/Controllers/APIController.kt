package com.example.weatherapp.Controllers

import com.example.weatherapp.Model.CityGuesses
import com.example.weatherapp.Model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.json.Json
import java.net.URL


@ImplicitReflectionSerializer
class APIController {
    fun APIController() = Unit

    @UnstableDefault
    suspend fun getForecastForCity(
        woeid: Int
    ): WeatherResponse {

        val result =
            withContext(Dispatchers.IO) {
                Json.nonstrict.parse(
                    WeatherResponse.serializer(),
                    URL("https://www.metaweather.com/api/location/" + woeid).readText()
                )
            }
        //println(Json.nonstrict.parse(WeatherResponse.serializer(),apiResponse))


        //callback.invoke(result)
        return result

    }

    suspend fun getCity(quer: String): List<CityGuesses> {
        val propositions = withContext(Dispatchers.IO) {
            Json.nonstrict.parse(
                ArrayListSerializer(CityGuesses.serializer()),
                URL("https://www.metaweather.com/api/location/search/?query=$quer").readText()
            )
        }
        return propositions
    }


}