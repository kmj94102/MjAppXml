package com.example.mjappxml.ui.game.elsword.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentElswordCounterAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElswordCounterAddFragment :
    BaseViewModelFragment<FragmentElswordCounterAddBinding, ElswordCounterAddViewModel>(R.layout.fragment_elsword_counter_add) {

    override val viewModel: ElswordCounterAddViewModel by viewModels()
    val adapter = ElswordCounterProgressAdapter {
        viewModel.deleteCounter(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.fragment = this
    }

    fun addCounter() {
        viewModel.addCounter(
            name = binding.editTitle.text.toString(),
            max = binding.editCount.text.toString().toInt(),
            onSuccess = {
                binding.editTitle.text?.clear()
                binding.editCount.text?.clear()
            }
        )
    }

}