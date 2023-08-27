package com.example.mjappxml.ui.game.pokemon.dex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.databinding.FragmentPokemonDexBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.mjappxml.R
import com.example.mjappxml.ui.game.pokemon.detail.PokemonDetailDialog

@AndroidEntryPoint
class PokemonDexFragment :
    BaseViewModelFragment<FragmentPokemonDexBinding, PokemonDexViewModel>(R.layout.fragment_pokemon_dex) {

    override val viewModel: PokemonDexViewModel by viewModels()
    private val adapter = PokemonSelectAdapter(::onDetailClick)
    private val detailDialog= PokemonDetailDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.adapter = adapter
        binding.viewModel = viewModel
    }

    fun onShinyStateChange() {
        val state = viewModel.toggleIsShiny()
        binding.btnShinyState.setImageRes(
            if (state) R.drawable.ic_shiny else R.drawable.ic_normal
        )
    }

    private fun onDetailClick(number: String) {
        detailDialog.setNumber(number)
        detailDialog.show(parentFragmentManager, "PokemonDetailDialog")
    }

}