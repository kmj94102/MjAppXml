package com.example.mjappxml.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.PokemonCounter
import com.example.mjappxml.databinding.CellHomePokemonCounterBinding

class HomePokemonCounterAdapter(
    private val onClick: () -> Unit
): ListAdapter<PokemonCounter, HomePokemonCounterViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePokemonCounterViewHolder =
        HomePokemonCounterViewHolder(
            CellHomePokemonCounterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: HomePokemonCounterViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), onClick)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonCounter>() {
            override fun areItemsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem == newItem
        }
    }
}

class HomePokemonCounterViewHolder(
    private val binding: CellHomePokemonCounterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: PokemonCounter,
        onClick: () -> Unit
    ) {
        binding.item = item
        binding.root.setOnClickListener { onClick() }
    }
}