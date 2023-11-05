package com.example.mjappxml.ui.accountbook.history

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.common.getToday
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

    fun goToAddNewItem() {
        (activity as? MainActivity)?.goToPage(
            R.id.navigation_account_book_add_new_item,
            bundleOf(
                Constants.Date to viewModel.selectDate.value
            )
        )
    }

    fun goToAddFixedItem() {
        (activity as? MainActivity)?.goToPage(
            R.id.navigation_account_book_fixed_item,
            bundleOf(
                Constants.Date to getToday("yyyy.MM")
            )
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchThisMonthDetail()
    }

}