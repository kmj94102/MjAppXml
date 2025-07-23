package com.example.mjappxml.ui.accountbook.add.fixed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.common.toast
import com.example.mjappxml.databinding.FragmentFixedAccountBookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixedAccountBookFragment :
    BaseViewModelFragment<FragmentFixedAccountBookBinding, FixedAccountBookViewModel>(R.layout.fragment_fixed_account_book) {

    override val viewModel: FixedAccountBookViewModel by viewModels()
    private lateinit var adapter: FixedAccountBookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FixedAccountBookAdapter(
            onClick = viewModel::updateSelectItem
        )
        binding.adapter = adapter
        binding.fragment = this
        binding.vm = viewModel
    }

    fun onSelect() {
        viewModel.getOnSelectItem()?.let {
            findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set(Constants.RESULT, it)
            findNavController().popBackStack()
        } ?: run {
            requireActivity().toast("고정내역을 선택해주세요.")
        }
    }
}