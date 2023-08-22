package com.example.mjappxml.ui.game.pokemon.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.PokemonCounter
import com.example.mjappxml.databinding.CellPokemonCounterBinding
import com.example.mjappxml.R

class PokemonCounterAdapter : ListAdapter<PokemonCounter, PokemonCounterViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PokemonCounterViewHolder(
            CellPokemonCounterBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_pokemon_counter, parent, false)
            )
        )

    override fun onBindViewHolder(holder: PokemonCounterViewHolder, position: Int) {
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonCounter>() {
            override fun areItemsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem.number == newItem.number
        }
    }
}

class PokemonCounterViewHolder(
    val binding: CellPokemonCounterBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {

    }
}