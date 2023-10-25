package com.example.mjappxml.ui.accountbook.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentMonthHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthHistoryFragment :
    BaseViewModelFragment<FragmentMonthHistoryBinding, MonthHistoryViewModel>(R.layout.fragment_month_history) {

    override val viewModel: MonthHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
        binding.adapter = MonthHistoryAdapter()

        binding.calendar.setOnChangeListener(viewModel::updateSelectDate)
    }

}