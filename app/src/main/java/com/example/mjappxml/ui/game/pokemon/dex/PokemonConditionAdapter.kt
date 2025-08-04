package com.example.mjappxml.ui.game.pokemon.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellPokemonConditionBinding

class PokemonConditionAdapter : ListAdapter<String, PokemonConditionViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PokemonConditionViewHolder(
        CellPokemonConditionBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_pokemon_condition, null, false)
        )
    )

    override fun onBindViewHolder(
        holder: PokemonConditionViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ) = oldItem == newItem

        }
    }
}

class PokemonConditionViewHolder(
    private val binding: CellPokemonConditionBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(value: String) {
        binding.value = value
    }
}