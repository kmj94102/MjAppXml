package com.example.mjappxml.ui.game.pokemon.dex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.databinding.FragmentPokemonDexBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.mjappxml.R
import com.example.mjappxml.common.GridSpaceItemDecoration

@AndroidEntryPoint
class PokemonDexFragment :
    BaseViewModelFragment<FragmentPokemonDexBinding, PokemonDexViewModel>(R.layout.fragment_pokemon_dex) {

    override val viewModel: PokemonDexViewModel by viewModels()
    private val adapter = PokemonSelectAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.adapter = adapter
        binding.viewModel = viewModel
        binding.recyclerView.addItemDecoration(
            GridSpaceItemDecoration(4, 10)
        )
    }

    fun onShinyStateChange() {
        val state = viewModel.toggleIsShiny()
        binding.btnShinyState.setImageRes(
            if (state) R.drawable.ic_shiny else R.drawable.ic_normal
        )
    }

}