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
    val adapter = GenerationDetailAdapter(::onDetailClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.vm = viewModel

        arguments?.getInt(Constants.INDEX)?.let(viewModel::fetchGenerationList) ?: onBack()
    }

    private fun onDetailClick(number: String, idx: Int, isCatch: Boolean) {
        GenerationDetailDialog(viewModel::updateIsCatch).also {
            it.setNumber(number, idx)
            it.show(parentFragmentManager, "$isCatch")
        }
    }

}