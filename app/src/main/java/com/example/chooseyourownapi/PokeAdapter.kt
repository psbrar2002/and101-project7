package com.example.chooseyourownapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Pokemon(val id: String, val name: String, val imageUrl: String)

class PokeAdapter(private val pokeList: List<Pokemon>) : RecyclerView.Adapter<PokeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView = view.findViewById(R.id.poke_image)
        val pokeName: TextView = view.findViewById(R.id.poke_name)
        val pokeId: TextView = view.findViewById(R.id.poke_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokeList[position]
        Glide.with(holder.itemView)
            .load(pokemon.imageUrl)
            .centerCrop()
            .into(holder.pokeImage)
        holder.pokeName.text = pokemon.name
        holder.pokeId.text = "ID: ${pokemon.id}"
    }

    override fun getItemCount() = pokeList.size
}