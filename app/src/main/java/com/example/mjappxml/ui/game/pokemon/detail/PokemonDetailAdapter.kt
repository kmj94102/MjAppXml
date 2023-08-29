package com.example.mjappxml.ui.game.pokemon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mjappxml.R
import com.example.mjappxml.custom.EvolutionItemView
import com.example.mjappxml.databinding.CellPokemonDetailInfoBinding
import com.example.mjappxml.databinding.CellPokemonEvolutionInfoBinding

class PokemonDetailAdapter : ListAdapter<PokemonDetailItem, ViewHolder>(diffUtil) {

    private val _isShiny = MutableLiveData(false)
    val isShiny: LiveData<Boolean> = _isShiny

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is PokemonDetailItem.Detail -> 0
            is PokemonDetailItem.Evolution -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> PokemonDetailViewHolder(
                CellPokemonDetailInfoBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_pokemon_detail_info, parent, false)
                ),
                parent.findViewTreeLifecycleOwner()
            )

            else -> PokemonEvolutionViewHolder(
                CellPokemonEvolutionInfoBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_pokemon_evolution_info, parent, false)
                ),
                parent.findViewTreeLifecycleOwner()
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is PokemonDetailViewHolder -> {
                holder.bind(currentList[position], this)
            }
            is PokemonEvolutionViewHolder -> {
                holder.bind(currentList[position], this)
            }
        }
    }

    fun updateIsShiny(value: Boolean) {
        _isShiny.value = value
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonDetailItem>() {
            override fun areItemsTheSame(
                oldItem: PokemonDetailItem,
                newItem: PokemonDetailItem
            ) = oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: PokemonDetailItem,
                newItem: PokemonDetailItem
            ) = oldItem == newItem
        }
    }
}

class PokemonDetailViewHolder(
    private val binding: CellPokemonDetailInfoBinding,
    private val lifecycleOwner: LifecycleOwner?
) : ViewHolder(binding.root) {
    fun bind(detailInfo: PokemonDetailItem, adapter: PokemonDetailAdapter) {
        if (detailInfo is PokemonDetailItem.Detail) {
            binding.info = detailInfo
            binding.adapter = adapter

            lifecycleOwner?.let {
                adapter.isShiny.observe(it) { binding.invalidateAll() }
            }
        }
    }

}

class PokemonEvolutionViewHolder(
    val binding: CellPokemonEvolutionInfoBinding,
    private val lifecycleOwner: LifecycleOwner?
) : ViewHolder(binding.root) {
    fun bind(detailInfo: PokemonDetailItem, adapter: PokemonDetailAdapter) {
        if (detailInfo is PokemonDetailItem.Evolution) {
            binding.info = detailInfo

            binding.layoutEvolution.removeAllViews()
            detailInfo.evolutionList.forEach { info ->
                binding.layoutEvolution.addView(
                    EvolutionItemView(binding.layoutEvolution.context).also {
                        it.setEvolutionInfo(info)
                    }
                )
            }

            lifecycleOwner?.let {
                adapter.isShiny.observe(it) { value ->
                    binding.invalidateAll()
                    binding.layoutEvolution.forEach { view ->
                        if (view is EvolutionItemView) {
                            view.updateShinyState(value)
                        }
                    }
                }
            }
        }
    }
}