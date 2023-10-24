package com.example.mjappxml.ui.game.pokemon.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
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
        binding.fragment = this
        binding.adapter = PokemonCounterAdapter(
            onSetting = { number, increase ->
                showIncreaseSettingDialog(number, increase)
            },
            onDelete = {
                viewModel.deleteCounter(it)
            },
            onUpdate = { number, increase ->
                viewModel.updateCounter(increase, number)
            },
            onGet = {
                viewModel.updateCatch(it)
            },
            onAdd = {}
        )
    }

    private fun showIncreaseSettingDialog(
        number: String,
        customIncrease: Int
    ) {
        IncreaseSettingDialog(
            increase = customIncrease,
            onSetting = { viewModel.updateCustomIncrease(number, it) }
        ).show(parentFragmentManager, "IncreaseSettingDialog")
    }

    fun goToCompleted() {
        (activity as? MainActivity)?.goToPage(R.id.navigation_completed_counter)
    }

}