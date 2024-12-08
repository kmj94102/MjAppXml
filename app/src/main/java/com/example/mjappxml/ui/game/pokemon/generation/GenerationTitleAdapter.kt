package com.example.mjappxml.ui.game.pokemon.generation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.GenerationCount
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellGenerationTitleBinding

class GenerationTitleAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<GenerationCount, GenerationTitleViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenerationTitleViewHolder(
            CellGenerationTitleBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cell_generation_title, parent, false
                )
            )
        )

    override fun onBindViewHolder(holder: GenerationTitleViewHolder, position: Int) {
        holder.bind(currentList[position], onClick)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GenerationCount>() {
            override fun areItemsTheSame(
                oldItem: GenerationCount,
                newItem: GenerationCount
            ) = oldItem.generationIdx == newItem.generationIdx

            override fun areContentsTheSame(
                oldItem: GenerationCount,
                newItem: GenerationCount
            ) = oldItem == newItem

        }
    }
}

class GenerationTitleViewHolder(
    val binding: CellGenerationTitleBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GenerationCount, onClick: (Int) -> Unit) {
        binding.item = item
        binding.root.setOnClickListener {
            onClick(item.generationIdx)
        }
    }
}