package com.example.mjappxml.ui.accountbook

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentAccountBookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountBookFragment :
    BaseViewModelFragment<FragmentAccountBookBinding, AccountBookViewModel>(R.layout.fragment_account_book) {

    override val viewModel: AccountBookViewModel by viewModels()
    private val adapter = LastMonthAnalysisAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.adapter = adapter
    }

}