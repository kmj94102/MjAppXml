package com.example.mjappxml.ui.game.elsword.introduce

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentElswordIntroduceBinding

@AndroidEntryPoint
class ElswordIntroduceFragment :
    BaseViewModelFragment<FragmentElswordIntroduceBinding, ElswordIntroduceViewModel>(R.layout.fragment_elsword_introduce) {

    override val viewModel: ElswordIntroduceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.fragment = this

    }

}