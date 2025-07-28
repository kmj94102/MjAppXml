package com.example.mjappxml.ui.game.pokemon.dex

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.databinding.FragmentPokemonDexBinding
import com.example.mjappxml.model.PokemonSearchItem
import com.example.mjappxml.ui.game.pokemon.detail.PokemonDetailDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDexFragment :
    BaseViewModelFragment<FragmentPokemonDexBinding, PokemonDexViewModel>(R.layout.fragment_pokemon_dex) {

    override val viewModel: PokemonDexViewModel by viewModels()
    private var adapter: PokemonSelectAdapter = PokemonSelectAdapter(::onDetailClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        fragment = this@PokemonDexFragment
        adapter = this@PokemonDexFragment.adapter
        vm = viewModel

        val navBackStackEntry = findNavController().currentBackStackEntry
        navBackStackEntry?.savedStateHandle?.getLiveData<PokemonSearchItem>(Constants.RESULT)
            ?.observe(viewLifecycleOwner) {
                viewModel.updateSearchInfo(it)
                navBackStackEntry.savedStateHandle.remove<PokemonSearchItem>(Constants.RESULT)
            }

        recyclerView.setOnScrollChangeListener { recyclerView, _, _, _, _ ->
            if (recyclerView.canScrollVertically(1).not()) {
                viewModel.fetchPokemonList()
            }
        }

        layoutNetworkError.btnBack.setOnClickListener { onBack() }
        layoutNetworkError.cardReload.setOnClickListener { viewModel.fetchPokemonList() }
    }

    fun goToSearch() {
        (activity as? MainActivity)?.goToPage(R.id.navigation_pokemon_search)
    }

    private fun onDetailClick(number: String, isCatch: Boolean) {
       PokemonDetailDialog(viewModel::updateIsCatch).also {
           it.setNumber(number)
           it.show(parentFragmentManager, "$isCatch")
       }
    }

    override fun updateNetworkErrorState(value: Boolean) {
        super.updateNetworkErrorState(value)
        binding.isError = value
    }
}