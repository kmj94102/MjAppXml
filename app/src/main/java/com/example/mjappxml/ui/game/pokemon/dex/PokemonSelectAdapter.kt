package com.example.mjappxml.ui.game.pokemon.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.PokemonSummary
import com.example.mjappxml.databinding.CellPokemonSelectBinding
import com.example.mjappxml.R

class PokemonSelectAdapter : ListAdapter<PokemonSummary, PokemonSelectViewHolder>(diffUtil) {

    private var isShiny = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSelectViewHolder =
        PokemonSelectViewHolder(
            CellPokemonSelectBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_pokemon_select, null, false)
            )
        )

    override fun onBindViewHolder(holder: PokemonSelectViewHolder, position: Int) {
        holder.bind(currentList[position], isShiny)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonSummary>() {
            override fun areItemsTheSame(
                oldItem: PokemonSummary,
                newItem: PokemonSummary
            ) = oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: PokemonSummary,
                newItem: PokemonSummary
            ) = oldItem == newItem

        }
    }

}

class PokemonSelectViewHolder(val binding: CellPokemonSelectBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonSummary: PokemonSummary, isShiny: Boolean) = with(binding) {
            pokemon = pokemonSummary
            this.isShiny = isShiny
            cardPokemon.setTopCardColor(
                if (pokemonSummary.isCatch) R.color.white else R.color.light_gray
            )
        }
}