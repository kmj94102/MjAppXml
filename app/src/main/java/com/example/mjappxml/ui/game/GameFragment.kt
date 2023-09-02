package com.example.mjappxml.ui.game

import android.os.Bundle
import android.view.View
import com.example.mjappxml.BaseViewFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentGameBinding

class GameFragment : BaseViewFragment<FragmentGameBinding>(R.layout.fragment_game) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
    }

    fun goToPokemonDex(view: View) {
        goToPage(R.id.navigation_pokemon_dex)
    }

    fun goToPokemonCounter(view: View) {
        goToPage(R.id.navigation_pokemon_counter)
    }

    fun goToElswordIntroduce(view: View) {
        goToPage(R.id.navigation_elsword_introduce)
    }

    fun goToElswordCounter(view: View) {
        goToPage(R.id.navigation_elsword_counter)
    }

    private fun goToPage(id: Int) {
        (activity as? MainActivity)?.goToPage(id)
    }

}