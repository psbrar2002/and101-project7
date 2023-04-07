package com.example.chooseyourownapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var pokeImageURL = ""
    var pokeName = ""
    var pokeId = ""
    //private var counter = 0
    private lateinit var pokeList: MutableList<String>
    private lateinit var rvPoke: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getPokeImageURL()
        Log.d("pokeImageURL", "poke image URL set")
        rvPoke = findViewById(R.id.poke_list)
        pokeList = mutableListOf()

        for (i in 0 until 20) {
            getPokeImageURL()
        }
        val adapter = PokeAdapter(pokeList)
        rvPoke.adapter = adapter
        rvPoke.layoutManager = LinearLayoutManager(this)
    }

    private fun getPokeImageURL(){
        val poke = Random.nextInt(1281)
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$poke/", object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Poke", "response successful$json")
                pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                pokeName = json.jsonObject.getString("name")
                pokeId = json.jsonObject.getInt("id").toString()
//                pokeList.add("$pokeName - #$pokeId")
                pokeList.add(pokeImageURL)
                val adapter = PokeAdapter(pokeList)
                rvPoke.adapter = adapter
                rvPoke.layoutManager = LinearLayoutManager(this@MainActivity)


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Poke Error", errorResponse)
            }
        }]
    }





}