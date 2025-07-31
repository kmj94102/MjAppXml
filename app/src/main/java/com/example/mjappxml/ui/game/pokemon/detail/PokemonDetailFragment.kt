package com.example.mjappxml.ui.game.pokemon.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.databinding.FragmentPokemonDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment
    : BaseViewModelFragment<FragmentPokemonDetailBinding, PokemonDetailViewModel>(R.layout.fragment_pokemon_detail) {

    override val viewModel: PokemonDetailViewModel by viewModels()
    private val weakAdapter = PokemonWeakAdapter()
    private val evolutionAdapter = PokemonEvolutionAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            vm = viewModel
            fragment = this@PokemonDetailFragment
            weakAdapter = this@PokemonDetailFragment.weakAdapter
            evolutionAdapter = this@PokemonDetailFragment.evolutionAdapter
        }
    }

    fun goToPrevPokemon() {
        viewModel.info.value.beforeInfo?.let {
            goToPage(it.number)
        }
    }

    fun goToNextPokemon() {
        viewModel.info.value.nextInfo?.let {
            goToPage(it.number)
        }
    }

    private fun goToPage(number: String) {
        findNavController().popBackStack()
        (activity as? MainActivity)?.goToPage(R.id.navigation_pokemon_detail, bundleOf(Constants.NUMBER to number))
    }

    fun updateIsShiny() {
        viewModel.updateIsShiny()
        evolutionAdapter.onShinyStateChange(viewModel.isShiny.value)
    }

}