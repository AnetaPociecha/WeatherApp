package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.Listeners.OnSwipeListener
import com.example.weatherapp.Model.Consolidated_Weather
import com.example.weatherapp.Model.DummyContent
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*
import kotlin.math.roundToInt


class ItemDetailFragment : Fragment() {

    private var cityPrognose: List<Consolidated_Weather>? = null
    private var idx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey("miasto")) {
                println(it.toString())

                cityPrognose = it.getParcelableArrayList("prognoza")
                activity?.toolbar_layout?.title = it.getCharSequence("miasto")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)


        rootView.nextDay.setOnClickListener {
            if (idx < cityPrognose?.size?.minus(1) ?: 5) idx++
            showPrognoseOfDay(idx, rootView)
        }

        rootView.prevDay.setOnClickListener {
            if (idx > 0) idx--
            showPrognoseOfDay(idx, rootView)
        }

        rootView.setOnTouchListener(object : OnSwipeListener() {
            override fun onSwipeLeft() {
                Log.e("ViewSwipe", "Left")
                if (idx > 0) idx--
                Log.e("index", idx.toString())
            }

            override fun onSwipeRight() {
                Log.e("ViewSwipe", "Right")
                if (idx < cityPrognose?.size ?: 9) idx++
                Log.e("index", idx.toString())
            }
        })

        showPrognoseOfDay(idx, rootView)
        return rootView
    }


    fun showPrognoseOfDay(day: Int, rootView: View) {
        rootView.forecastDate.text = cityPrognose?.get(day)?.applicable_date
        rootView.maxTemp.text =
            cityPrognose?.get(day)?.max_temp?.roundToInt().toString() + " \u2103"
        rootView.minTemp.text =
            cityPrognose?.get(day)?.min_temp?.roundToInt().toString() + " \u2103"
        rootView.wind_direction.text =
            cityPrognose?.get(day)?.wind_direction?.roundToInt().toString() + " " + cityPrognose?.get(
                day
            )?.wind_direction_compass
        rootView.wind_speed.text =
            "%.2f".format(cityPrognose?.get(day)?.wind_speed?.times(1.609344)) + " kph"
        rootView.air_pressure.text =
            cityPrognose?.get(day)?.air_pressure?.roundToInt().toString() + " mbar"
        rootView.vis.text = "%.2f".format(cityPrognose?.get(day)?.visibility) + " mil"
        rootView.predictability.text =
            "%d".format(cityPrognose?.get(day)?.predictability) + " %"
        rootView.humidity.text = "%.2f".format(cityPrognose?.get(day)?.humidity) + " %"


    }


}
