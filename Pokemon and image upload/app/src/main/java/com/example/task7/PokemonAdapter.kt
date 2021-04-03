package com.example.task7

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task7.databinding.ItemLayoutBinding
import com.example.task7.model.Result
import com.example.task7.util.getId

class PokemonAdapter(
        private var result: ArrayList<Result>,
        private val listener: OnItemClickListener,
        private var context: Context)
    : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {


    inner class ViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.itemImage
        val name = binding.itemName

        //Set the clicklistener on the item view
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {
       val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = result.size

    override fun onBindViewHolder(holder: PokemonAdapter.ViewHolder, position: Int) {
        val resultItem = result[position]
        holder.name.text = resultItem.name.toUpperCase()

        //Extract the id of each pokemon and use it to fetch the associated image
        val id = getId(resultItem.url)
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png")
                .into(holder.image)
    }

    //Interface for implementing the onClick event of adapter items
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}