package com.example.chooseyourownapi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var pokeImageURL = ""
    var pokeName = ""
    var pokeId = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPokeImageURL()
        Log.d("pokeImageURL", "poke image URL set")
        val button = findViewById<Button>(R.id.pokeButton)
        val imageView = findViewById<ImageView>(R.id.pokeImage)
        val textView = findViewById<TextView>(R.id.pokeNameText)
        val textView2 = findViewById<TextView>(R.id.pokeid)
        getNextImage(button,imageView,textView,textView2)
    }
    private fun getNextImage(button: Button, imageView: ImageView,textView:TextView,textView2:TextView) {
        button.setOnClickListener {
            getPokeImageURL()

            Glide.with(this)
                . load(pokeImageURL)
                .fitCenter()
                .into(imageView)
                textView.text =pokeName
                textView2.text=pokeId
        }

    }

    private fun getPokeImageURL(){
        val poke = Random.nextInt(1281)
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$poke/", object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Poke", "response successful$json")
                pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                pokeName =json.jsonObject.getString("name")
                pokeId=json.jsonObject.getString("id")
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