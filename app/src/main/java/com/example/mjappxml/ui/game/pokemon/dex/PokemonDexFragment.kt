package com.example.mjappxml.ui.game.pokemon.dex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.databinding.FragmentPokemonDexBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.mjappxml.R
import com.example.mjappxml.common.GridSpaceItemDecoration
import com.example.mjappxml.ui.game.pokemon.detail.PokemonDetailDialog

@AndroidEntryPoint
class PokemonDexFragment :
    BaseViewModelFragment<FragmentPokemonDexBinding, PokemonDexViewModel>(R.layout.fragment_pokemon_dex) {

    override val viewModel: PokemonDexViewModel by viewModels()
    private var adapter: PokemonSelectAdapter = PokemonSelectAdapter(::onDetailClick)
    private val detailDialog= PokemonDetailDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.adapter = adapter
        binding.viewModel = viewModel

        binding.recyclerView.addItemDecoration(
            GridSpaceItemDecoration(4, 10)
        )
        binding.recyclerView.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            if (view.canScrollVertically(1).not()) {
                viewModel.fetchPokemonList()
            }
        }
    }

    fun onShinyStateChange() {
        viewModel.toggleIsShiny()
        adapter.onShinyStateChange(viewModel.isShiny.value)
    }

    private fun onDetailClick(number: String) {
        detailDialog.setNumber(number)
        detailDialog.show(parentFragmentManager, "PokemonDetailDialog")
    }

}