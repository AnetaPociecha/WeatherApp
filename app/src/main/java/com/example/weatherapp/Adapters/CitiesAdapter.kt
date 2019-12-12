package com.example.weatherapp.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.weatherapp.Model.CityGuesses

class CitiesAdapter(private val activity: Activity, arry: List<CityGuesses>) : BaseAdapter() {

    private var arry = ArrayList<CityGuesses>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vi: View = convertView as View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(android.R.layout.simple_list_item_1, null)
        val name = vi.findViewById<TextView>(android.R.id.text1)
        name.text = arry.get(position).title
        return vi
    }

    override fun getItem(position: Int): Any {
        return arry.get(position)
    }

    override fun getItemId(position: Int): Long {
        return arry.get(position).woeid.toLong()
    }

    override fun getCount(): Int {
        return arry.size
    }
}