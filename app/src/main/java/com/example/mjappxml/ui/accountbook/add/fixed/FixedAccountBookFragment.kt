package com.example.mjappxml.ui.accountbook.add.fixed

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentFixedAccountBookBinding
import com.example.mjappxml.ui.dialog.YearMonthSelectDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixedAccountBookFragment :
    BaseViewModelFragment<FragmentFixedAccountBookBinding, FixedAccountBookViewModel>(R.layout.fragment_fixed_account_book) {

    override val viewModel: FixedAccountBookViewModel by viewModels()
    private lateinit var adapter: FixedAccountBookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FixedAccountBookAdapter(
            onDateClick = {},
            onDeleteClick = {},
            onRegisterClick = {}
        )
        binding.adapter = adapter
        binding.fragment = this
        binding.vm = viewModel
    }

    override fun onResume() {
        super.onResume()
//        viewModel.fetchFixedAccountBook()
    }

    fun goToAddIem() {
        (activity as? MainActivity)?.goToPage(R.id.navigation_account_book_add_fixed_item)
    }

    fun showYearMonthSelectDialog() {
        YearMonthSelectDialog(viewModel::updateYearMonth).also {
            it.setPrimaryColor(ContextCompat.getColor(requireContext(), R.color.turquoise))
            it.show(parentFragmentManager, viewModel.yearMonth.value)
        }
    }

}