package com.example.mjappxml.ui.game.pokemon.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentPokemonCounterBinding

@AndroidEntryPoint
class PokemonCountFragment :
    BaseViewModelFragment<FragmentPokemonCounterBinding, PokemonCounterViewModel>(R.layout.fragment_pokemon_counter) {

    override val viewModel: PokemonCounterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.adapter = PokemonCounterAdapter()
    }

}