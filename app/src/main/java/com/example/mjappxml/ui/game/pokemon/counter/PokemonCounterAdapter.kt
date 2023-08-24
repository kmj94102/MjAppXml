package com.example.mjappxml.ui.game.pokemon.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.communication.model.PokemonCounter
import com.example.mjappxml.databinding.CellPokemonCounterBinding
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellPokemonCounterAddBinding

class PokemonCounterAdapter : ListAdapter<PokemonCounter, ViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int) =
        if (currentList[position].number.isEmpty()) Add
        else Counter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            Counter -> PokemonCounterViewHolder(
                CellPokemonCounterBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_pokemon_counter, parent, false)
                )
            )

            else -> PokemonAddViewHolder(
                CellPokemonCounterAddBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_pokemon_counter_add, parent, false)
                )
            )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is PokemonCounterViewHolder -> {
                holder.bind(currentList[position])
            }
            is PokemonAddViewHolder -> {
                holder.bind()
            }
        }
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

        const val Counter = 1
        const val Add = 2
    }
}

class PokemonCounterViewHolder(
    val binding: CellPokemonCounterBinding
) : ViewHolder(binding.root) {
    fun bind(pokemonCounter: PokemonCounter) {
        binding.pokemon = pokemonCounter
    }
}

class PokemonAddViewHolder(
    val binding: CellPokemonCounterAddBinding
) : ViewHolder(binding.root) {
    fun bind() {

    }
}