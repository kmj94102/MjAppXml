package com.example.mjappxml.ui.game.pokemon.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.databinding.FragmentPokemonSearchBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class PokemonSearchFragment() :
    BaseViewModelFragment<FragmentPokemonSearchBinding, PokemonSearchViewModel>(R.layout.fragment_pokemon_search) {
    override val viewModel: PokemonSearchViewModel by viewModels()
    private val typeAdapter: PokemonTypeAdapter by lazy { PokemonTypeAdapter(viewModel::updateTypeSelect) }
    private val generationAdapter: PokemonGenerationAdapter by lazy {
        PokemonGenerationAdapter(viewModel::updateGenerationSelect)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            fragment = this@PokemonSearchFragment
            vm = viewModel
            typeAdapter = this@PokemonSearchFragment.typeAdapter
            generationAdapter = this@PokemonSearchFragment.generationAdapter

            rvType.layoutManager = FlexboxLayoutManager(this@PokemonSearchFragment.context).apply {
                justifyContent = JustifyContent.FLEX_START
            }
            rvGeneration.layoutManager = FlexboxLayoutManager(this@PokemonSearchFragment.context).apply {
                justifyContent = JustifyContent.FLEX_START
            }
        }
    }

    fun onSearch() {
        findNavController()
            .previousBackStackEntry
            ?.savedStateHandle
            ?.set(Constants.RESULT, viewModel.pokemonSearchItem.value)
        findNavController().popBackStack()
    }
}