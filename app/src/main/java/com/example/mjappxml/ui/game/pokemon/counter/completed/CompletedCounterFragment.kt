package com.example.mjappxml.ui.game.pokemon.counter.completed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentCompletedCounterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletedCounterFragment :
    BaseViewModelFragment<FragmentCompletedCounterBinding, CompletedCounterViewModel>(R.layout.fragment_completed_counter) {

    override val viewModel: CompletedCounterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
        binding.adapter = CompletedCounterAdapter(
            onDeleteClick = viewModel::updateDelete,
            onRestoreClick = viewModel::updateRestore
        )

    }

}