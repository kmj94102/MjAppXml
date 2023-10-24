package com.example.mjappxml.ui.game.pokemon.counter.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.PokemonCounter
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellCompletedCounterBinding

class CompletedCounterAdapter(
    private val onDeleteClick: (Int) -> Unit,
    private val onRestoreClick: (String, Int) -> Unit
) : ListAdapter<PokemonCounter, CompletedCounterViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompletedCounterViewHolder(
            CellCompletedCounterBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.cell_completed_counter, parent, false)
            )
        )

    override fun onBindViewHolder(holder: CompletedCounterViewHolder, position: Int) {
        holder.bind(
            currentList[position],
            onDeleteClick,
            onRestoreClick
        )
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonCounter>() {
            override fun areItemsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem.index == newItem.index

            override fun areContentsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem == newItem
        }
    }
}

class CompletedCounterViewHolder(private val binding: CellCompletedCounterBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PokemonCounter,
        onDeleteClick: (Int) -> Unit,
        onRestoreClick: (String, Int) -> Unit
    ) {
        binding.item = item
        binding.btnDelete.setOnClickListener{ onDeleteClick(item.index) }
        binding.btnRestore.setOnClickListener{ onRestoreClick(item.number, item.index) }
    }
}