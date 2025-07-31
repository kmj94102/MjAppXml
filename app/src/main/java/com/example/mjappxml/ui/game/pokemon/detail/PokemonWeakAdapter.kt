package com.example.mjappxml.ui.game.pokemon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.communication.model.PokemonWeakInfo
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.databinding.CellPokomonWeekBinding

class PokemonWeakAdapter: ListAdapter<PokemonWeakInfo, PokemonWeakViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PokemonWeakViewHolder(
        CellPokomonWeekBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_pokomon_week, parent, false)
        )
    )

    override fun onBindViewHolder(
        holder: PokemonWeakViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonWeakInfo>() {
            override fun areItemsTheSame(
                oldItem: PokemonWeakInfo,
                newItem: PokemonWeakInfo
            ) = oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: PokemonWeakInfo,
                newItem: PokemonWeakInfo
            ) = oldItem == newItem

        }
    }

}

class PokemonWeakViewHolder(private val binding: CellPokomonWeekBinding): ViewHolder(binding.root) {
    fun bind(info: PokemonWeakInfo) {
        binding.info = info
        info.imageRes?.forEach { imageRes ->
            val context = binding.layout.context
            val imageView = ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    context.dpToPx(50), context.dpToPx(50)
                ).apply {
                    setMargins(0, 0, context.dpToPx(10), context.dpToPx(10))
                }
                setImageResource(imageRes)
                scaleType = ImageView.ScaleType.FIT_XY
            }
            binding.layout.addView(imageView)
        }
    }
}