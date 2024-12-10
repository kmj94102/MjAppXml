package com.example.mjappxml.ui.game.pokemon.generation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.databinding.FragmentGenerationDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerationDetailFragment :
    BaseViewModelFragment<FragmentGenerationDetailBinding, GenerationDetailViewModel>(R.layout.fragment_generation_detail) {
    override val viewModel: GenerationDetailViewModel by viewModels()
    val adapter = GenerationDetailAdapter{ _, _-> }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.vm = viewModel

        arguments?.getInt(Constants.INDEX)?.let(viewModel::fetchGenerationList) ?: onBack()
    }

}