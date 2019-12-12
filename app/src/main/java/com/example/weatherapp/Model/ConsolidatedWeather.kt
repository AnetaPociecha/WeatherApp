package com.example.weatherapp.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable


@Serializable
data class Consolidated_Weather (
    val applicable_date: String,
    val weather_state_name:String,
    val weather_state_abbr: String,
    val wind_speed: Float,
    val wind_direction: Float,
    val wind_direction_compass:String,
    val max_temp:Float,
    val min_temp:Float,
    val air_pressure:Float,
    val humidity:Float,
    val visibility:Float,
    val predictability:Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(applicable_date)
        parcel.writeString(weather_state_name)
        parcel.writeString(weather_state_abbr)
        parcel.writeFloat(wind_speed)
        parcel.writeFloat(wind_direction)
        parcel.writeString(wind_direction_compass)
        parcel.writeFloat(max_temp)
        parcel.writeFloat(min_temp)
        parcel.writeFloat(air_pressure)
        parcel.writeFloat(humidity)
        parcel.writeFloat(visibility)
        parcel.writeInt(predictability)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Consolidated_Weather> {
        override fun createFromParcel(parcel: Parcel): Consolidated_Weather {
            return Consolidated_Weather(parcel)
        }

        override fun newArray(size: Int): Array<Consolidated_Weather?> {
            return arrayOfNulls(size)
        }
    }
}