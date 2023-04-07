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
    private lateinit var pokeList: MutableList<Pokemon>
    private lateinit var rvPoke: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPoke = findViewById(R.id.poke_list)
        pokeList = mutableListOf()

        for (i in 0 until 20) {
            getPokemonData()
        }

        val adapter = PokeAdapter(pokeList)
        rvPoke.adapter = adapter
        rvPoke.layoutManager = LinearLayoutManager(this)
    }

    private fun getPokemonData() {
        val pokeId = Random.nextInt(1, 898)
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokeId/", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val pokemonData = json.jsonObject
                val pokeImageURL = pokemonData.getJSONObject("sprites").getString("front_default")
                val pokeName = pokemonData.getString("name")
                val pokeId = pokemonData.getInt("id")

                val pokemon = Pokemon(pokeName, pokeId.toString(), pokeImageURL)
                pokeList.add(pokemon)

                rvPoke.adapter?.notifyDataSetChanged()
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