package com.example.mjappxml.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentHomeBinding
import com.example.mjappxml.ui.game.pokemon.counter.PokemonCounterAdapter
import com.example.mjappxml.ui.schedule.ScheduleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    val adapter = HomePokemonCounterAdapter(::goToPokemonCounter)
    val scheduleAdapter = ScheduleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel

    }

    fun goToSchedule() {
        findNavController().navigate(R.id.navigation_schedule)
    }

    fun goToPokemonCounter() {
        findNavController().navigate(R.id.navigation_pokemon_counter)
    }
}