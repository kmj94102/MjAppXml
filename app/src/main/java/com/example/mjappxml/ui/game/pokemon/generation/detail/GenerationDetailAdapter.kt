package com.example.mjappxml.ui.game.pokemon.generation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.GenerationDex
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellGenerationDexBinding

class GenerationDetailAdapter(
    private val onItemClick: (String, Int, Boolean) -> Unit,
) : ListAdapter<GenerationDex, GenerationDetailViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenerationDetailViewHolder =
        GenerationDetailViewHolder(
            CellGenerationDexBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_generation_dex, null, false)
            )
        )

    override fun onBindViewHolder(holder: GenerationDetailViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClick)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GenerationDex>() {
            override fun areItemsTheSame(
                oldItem: GenerationDex,
                newItem: GenerationDex
            ) = oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: GenerationDex,
                newItem: GenerationDex
            ) = oldItem == newItem

        }
    }

}

class GenerationDetailViewHolder(
    val binding: CellGenerationDexBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: GenerationDex,
        onItemClick: (String, Int, Boolean) -> Unit
    ) {
        binding.root.setOnClickListener { onItemClick(item.number, item.idx, item.isCatch) }
        binding.pokemon = item
        binding.cardPokemon.setTopCardColor(
            if (item.isCatch) R.color.white else R.color.light_gray
        )
    }
}