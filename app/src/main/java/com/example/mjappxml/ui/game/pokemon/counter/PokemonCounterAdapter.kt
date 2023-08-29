package com.example.mjappxml.ui.game.pokemon.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.communication.model.PokemonCounter
import com.example.mjappxml.databinding.CellPokemonCounterBinding
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellPokemonCounterAddBinding

class PokemonCounterAdapter(
    private val onSetting: (String, Int) -> Unit,
    private val onDelete: (String) -> Unit,
    private val onUpdate: (String, Int) -> Unit,
    private val onGet: (String) -> Unit,
    private val onAdd: () -> Unit
) : ListAdapter<PokemonCounter, ViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int) =
        if (currentList[position].number.isEmpty()) Add
        else Counter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            Counter -> PokemonCounterViewHolder(
                CellPokemonCounterBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_pokemon_counter, parent, false)
                )
            )

            else -> PokemonAddViewHolder(
                CellPokemonCounterAddBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cell_pokemon_counter_add, parent, false)
                )
            )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PokemonCounterViewHolder -> {
                holder.bind(
                    pokemonCounter = currentList[position],
                    onSetting = onSetting,
                    onDelete = onDelete,
                    onUpdate = onUpdate,
                    onGet = onGet
                )
            }

            is PokemonAddViewHolder -> {
                holder.bind(onAdd = onAdd)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PokemonCounter>() {
            override fun areItemsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PokemonCounter,
                newItem: PokemonCounter
            ) = oldItem.number == newItem.number
        }

        const val Counter = 1
        const val Add = 2
    }
}

class PokemonCounterViewHolder(
    val binding: CellPokemonCounterBinding
) : ViewHolder(binding.root) {
    fun bind(
        pokemonCounter: PokemonCounter,
        onSetting: (String, Int) -> Unit,
        onUpdate: (String, Int) -> Unit,
        onDelete: (String) -> Unit,
        onGet: (String) -> Unit
    ) = with(binding) {
        pokemon = pokemonCounter
        btnSetting.setOnClickListener {
            onSetting(
                pokemonCounter.number,
                pokemonCounter.customIncrease
            )
        }
        btnClose.setOnClickListener { onDelete(pokemonCounter.number) }
        btnGet.setOnClickListener { onGet(pokemonCounter.number) }

        btnDecreaseOne.setOnClickListener {
            onUpdate(
                pokemonCounter.number,
                pokemonCounter.count - 1
            )
        }
        btnDecreaseCustom.setOnClickListener {
            onUpdate(
                pokemonCounter.number,
                pokemonCounter.count - pokemonCounter.customIncrease
            )
        }
        btnIncreaseOne.setOnClickListener {
            onUpdate(
                pokemonCounter.number,
                pokemonCounter.count + 1
            )
        }
        btnIncreaseCustom.setOnClickListener {
            onUpdate(
                pokemonCounter.number,
                pokemonCounter.count + pokemonCounter.customIncrease
            )
        }
    }

    private fun calculate(increase : Int) {

    }
}

class PokemonAddViewHolder(
    val binding: CellPokemonCounterAddBinding
) : ViewHolder(binding.root) {
    fun bind(onAdd: () -> Unit) {
        binding.root.setOnClickListener { onAdd() }
    }
}