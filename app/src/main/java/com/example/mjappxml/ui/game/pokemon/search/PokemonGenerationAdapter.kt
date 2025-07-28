package com.example.mjappxml.ui.game.pokemon.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellPinkSelectChipBinding
import com.example.mjappxml.model.PokemonSearchGeneration

class PokemonGenerationAdapter(
    private val onClick: (String) -> Unit
): ListAdapter<PokemonSearchGeneration, PokemonGenerationViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PokemonGenerationViewHolder(
        CellPinkSelectChipBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_pink_select_chip, null, false)
        )
    )

    override fun onBindViewHolder(
        holder: PokemonGenerationViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position], onClick)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonSearchGeneration>() {
            override fun areItemsTheSame(
                oldItem: PokemonSearchGeneration,
                newItem: PokemonSearchGeneration
            ) = oldItem.generation == newItem.generation

            override fun areContentsTheSame(
                oldItem: PokemonSearchGeneration,
                newItem: PokemonSearchGeneration
            ) = oldItem == newItem

        }
    }
}

class PokemonGenerationViewHolder(
    private val binding: CellPinkSelectChipBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PokemonSearchGeneration,
        onClick: (String) -> Unit
    ) {
        with(binding) {
            contents = item.generation
            isSelect = item.isSelect
            root.setOnClickListener { onClick(item.generation) }
        }
    }
}