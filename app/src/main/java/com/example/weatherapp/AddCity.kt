package com.example.weatherapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Adapters.CitiesAdapter
import com.example.weatherapp.Controllers.APIController
import com.example.weatherapp.Model.CityGuesses
import com.example.weatherapp.Model.WeatherResponse
import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ImplicitReflectionSerializer
import java.io.Serializable
import kotlin.math.roundToInt

class AddCity : AppCompatActivity() {
    var list: ListView = this.findViewById(R.id.cities)
    var btn: Button = findViewById(R.id.searchButton)
    var queryEdit: EditText = findViewById(R.id.query)
    @ImplicitReflectionSerializer
    val api: APIController = APIController()

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        var cities: List<CityGuesses> = ArrayList()
        btn.setOnClickListener {
            var tex = queryEdit.text
            runBlocking {
                cities = api.getCity(tex.toString())

            }
            list.adapter = CitiesAdapter(this, cities)
        }

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            data.
//        }
//    }


}
