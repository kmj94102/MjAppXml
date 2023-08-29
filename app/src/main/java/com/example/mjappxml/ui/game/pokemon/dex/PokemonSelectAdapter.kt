package com.example.mjappxml.ui.game.pokemon.dex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.PokemonSummary
import com.example.mjappxml.databinding.CellPokemonSelectBinding
import com.example.mjappxml.R

class PokemonSelectAdapter(
    val onItemClick: (String) -> Unit,
) : ListAdapter<PokemonSummary, PokemonSelectViewHolder>(diffUtil) {

    private val _isShiny = MutableLiveData(false)
    val isShiny: LiveData<Boolean> = _isShiny

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSelectViewHolder =
        PokemonSelectViewHolder(
            CellPokemonSelectBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_pokemon_select, null, false)
            ),
            parent.findViewTreeLifecycleOwner()
        )

    override fun onBindViewHolder(holder: PokemonSelectViewHolder, position: Int) {
        holder.bind(currentList[position], this)
    }

    fun onShinyStateChange(value: Boolean) {
        _isShiny.value = value
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonSummary>() {
            override fun areItemsTheSame(
                oldItem: PokemonSummary,
                newItem: PokemonSummary
            ) = oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: PokemonSummary,
                newItem: PokemonSummary
            ) = oldItem == newItem

        }
    }

}

class PokemonSelectViewHolder(
    val binding: CellPokemonSelectBinding,
    private val lifecycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        pokemonSummary: PokemonSummary,
        adapter: PokemonSelectAdapter
    ) {
        binding.root.setOnClickListener { adapter.onItemClick(pokemonSummary.number) }
        binding.pokemon = pokemonSummary
        binding.cardPokemon.setTopCardColor(
            if (pokemonSummary.isCatch) R.color.white else R.color.light_gray
        )
        lifecycleOwner?.let {
            adapter.isShiny.observe(it) { binding.invalidateAll() }
        }
    }
}