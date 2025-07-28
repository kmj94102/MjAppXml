package com.example.mjappxml.ui.game.pokemon.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellPinkSelectChipBinding
import com.example.mjappxml.model.PokemonSearchType

class PokemonTypeAdapter(
    private val onClick: (String) -> Unit
): ListAdapter<PokemonSearchType, PokemonTypeViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PokemonTypeViewHolder(
        CellPinkSelectChipBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_pink_select_chip, null, false)
        )
    )

    override fun onBindViewHolder(
        holder: PokemonTypeViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position], onClick)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonSearchType>() {
            override fun areItemsTheSame(
                oldItem: PokemonSearchType,
                newItem: PokemonSearchType
            ) = oldItem.type == newItem.type

            override fun areContentsTheSame(
                oldItem: PokemonSearchType,
                newItem: PokemonSearchType
            ) = oldItem == newItem

        }
    }
}

class PokemonTypeViewHolder(
    private val binding: CellPinkSelectChipBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PokemonSearchType,
        onClick: (String) -> Unit
    ) {
        with(binding) {
            contents = item.type
            isSelect = item.isSelect
            root.setOnClickListener { onClick(item.type) }
        }
    }
}