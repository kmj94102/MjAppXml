package com.example.mjappxml.ui.game.pokemon.generation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentGenerationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerationFragment : BaseViewModelFragment<FragmentGenerationBinding, GenerationViewModel>(R.layout.fragment_generation) {

    override val viewModel: GenerationViewModel by viewModels()
    val adapter = GenerationTitleAdapter {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
    }

}