package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.weatherapp.Controllers.APIController

import com.example.weatherapp.Model.WeatherResponse
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import org.jetbrains.anko.contentView
import org.jetbrains.anko.startActivityForResult
import java.io.Serializable
import kotlin.math.roundToInt

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    @ImplicitReflectionSerializer
    private var apiCtrl: APIController = APIController()
    var forecastList: ArrayList<WeatherResponse> = ArrayList()
    var woeids = listOf(44418, 523920, 2487956)
    private val job = Job()


    @SuppressLint("ResourceType")
    @UnstableDefault
    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = "TEST"

        runBlocking {
            for (woe in woeids)
                forecastList.add(apiCtrl.getForecastForCity(woe))

        }


        addCityBtnn.setOnClickListener { view ->
            startActivityForResult<AddCity>(1)
        }
        Log.d("Dane", forecastList.toString())

        if (item_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView(item_list)

        logToFirebase()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, forecastList, twoPane)
    }

    private fun logToFirebase() {
        val database = FirebaseDatabase.getInstance()
        val logs = database.getReference("logs")

        val id = logs.push().key
        val currentTimestamp = System.currentTimeMillis()
        logs.child("log" + id.toString()).setValue(currentTimestamp.toString())
    }


    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<WeatherResponse>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as WeatherResponse
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString("miasto", item.title)
                            putSerializable(
                                "prognoza",
                                item.consolidated_weather as Serializable
                            )
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra("miasto", item.title)
                        putExtra("prognoza", item.consolidated_weather)

                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.cityNameView.text = item.title
            holder.temperatureView.text =
                item.consolidated_weather?.first()?.max_temp!!.roundToInt().toString() + " \u2103"

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cityNameView: TextView = view.city_name
            val temperatureView: TextView = view.temperature
        }
    }
}
