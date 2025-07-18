package com.example.mjappxml.ui.accountbook

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.common.getToday
import com.example.mjappxml.databinding.FragmentAccountBookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountBookFragment :
    BaseViewModelFragment<FragmentAccountBookBinding, AccountBookViewModel>(R.layout.fragment_account_book) {

    override val viewModel: AccountBookViewModel by viewModels()
    private val adapter = AccountBookAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
        binding.adapter = adapter
    }

    fun goToMonthHistory() {
        (activity as? MainActivity)?.goToPage(R.id.navigation_month_history)
    }

    fun goToAddNewItem() {
        (activity as? MainActivity)?.goToPage(
            R.id.navigation_account_book_add_new_item,
            bundleOf(
                Constants.Date to getToday()
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

}