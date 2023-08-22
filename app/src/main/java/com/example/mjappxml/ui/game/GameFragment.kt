package com.example.mjappxml.ui.game

import android.os.Bundle
import android.view.View
import com.example.mjappxml.BaseViewFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentGameBinding

class GameFragment : BaseViewFragment<FragmentGameBinding>(R.layout.fragment_game) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
    }

    fun goToPokemonDex(view: View) {
    }

    fun goToPokemonCounter(view: View) {
    }

    fun goToElswordIntroduce(view: View) {
    }

    fun goToElswordCounter(view: View) {
    }

}