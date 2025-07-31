package com.example.mjappxml.ui.game.pokemon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.EvolutionInfo
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellPokemonEvolutionBinding

class PokemonEvolutionAdapter : ListAdapter<EvolutionInfo, PokemonEvolution2ViewHolder>(diffUtil) {
    private val _isShiny = MutableLiveData(false)
    val isShiny: LiveData<Boolean> = _isShiny

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PokemonEvolution2ViewHolder(
        CellPokemonEvolutionBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_pokemon_evolution, parent, false)
        ),
        parent.findViewTreeLifecycleOwner()
    )

    override fun onBindViewHolder(
        holder: PokemonEvolution2ViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position], this)
    }

    fun onShinyStateChange(value: Boolean) {
        _isShiny.value = value
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<EvolutionInfo>() {
            override fun areItemsTheSame(
                oldItem: EvolutionInfo,
                newItem: EvolutionInfo
            ) = oldItem.afterNumber == newItem.afterNumber

            override fun areContentsTheSame(
                oldItem: EvolutionInfo,
                newItem: EvolutionInfo
            ) = oldItem == newItem
        }
    }
}

class PokemonEvolution2ViewHolder(
    private val binding: CellPokemonEvolutionBinding,
    private val lifecycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        info: EvolutionInfo,
        adapter: PokemonEvolutionAdapter
    ) {
        binding.info = info
        binding.adapter = adapter

        lifecycleOwner?.let {
            adapter.isShiny.observe(it) { binding.invalidateAll() }
        }
    }
}