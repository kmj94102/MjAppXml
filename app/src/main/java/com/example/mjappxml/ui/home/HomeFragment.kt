package com.example.mjappxml.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentHomeBinding
import com.example.mjappxml.ui.game.pokemon.counter.PokemonCounterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    val adapter = PokemonCounterAdapter(
        onSetting = { _, _ -> },
        onDelete = {},
        onGet = {},
        onUpdate = { _, _ -> },
        onAdd = {}
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel

    }
}