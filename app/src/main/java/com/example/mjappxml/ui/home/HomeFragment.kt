package com.example.mjappxml.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.communication.model.PokemonCounter
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.GridSpaceItemDecoration
import com.example.mjappxml.databinding.FragmentHomeBinding
import com.example.mjappxml.ui.game.pokemon.counter.PokemonCounterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonCounterAdapter(
            onSetting = { _, _ -> },
            onDelete = {},
            onGet = {},
            onUpdate = { _, _ -> },
            onAdd = {}
        )

        adapter.submitList(
            listOf(
                PokemonCounter.init(),
                PokemonCounter.init(),
                PokemonCounter.init(),
            )
        )

        binding.recyclerPokemonCounter.adapter = adapter
        binding.recyclerPokemonCounter.addItemDecoration(GridSpaceItemDecoration(2, 15))

    }
}