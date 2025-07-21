package com.example.mjappxml.ui.accountbook.add.fixed

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.communication.util.removeNumberFormat
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentAddFixedAccountBookBinding
import com.example.mjappxml.ui.accountbook.add.WhereToUseAdapter
import com.example.mjappxml.ui.dialog.SelectOneDialog
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFixedAccountBookFragment :
    BaseViewModelFragment<FragmentAddFixedAccountBookBinding, AddFixedAccountBookViewModel>(R.layout.fragment_add_fixed_account_book) {
    override val viewModel: AddFixedAccountBookViewModel by viewModels()

    private lateinit var adapter: WhereToUseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel

        adapter = WhereToUseAdapter { viewModel.updateWhereToUse(it) }
        binding.adapter = adapter
        binding.recyclerView.layoutManager = FlexboxLayoutManager(this.context).apply {
            justifyContent = JustifyContent.SPACE_BETWEEN
        }
    }

    fun updateIncome() {
        viewModel.updateIsIncome(true)
    }

    fun updateExpenditure() {
        viewModel.updateIsIncome(false)
    }

    fun doRegister() {
        runCatching {
            viewModel.insertFixedAccountBookItem(
                binding.editAmount.text.toString().removeNumberFormat().toInt()
            )
        }
    }

    fun showDateSelectDialog() {
        val array = (1..30)
            .map { it.toString().padStart(2, '0').plus("일") }
            .toList()
            .toTypedArray()
        val index = array.indexOfFirst { it.dropLast(1) == viewModel.item.value.date }

        SelectOneDialog {
            viewModel.updateDate(array[it])
        }.also {
            it.setPickerValue(array)
            it.setPrimaryColor(ContextCompat.getColor(requireContext(), R.color.turquoise))
            it.show(parentFragmentManager, "$index")
        }
    }
}