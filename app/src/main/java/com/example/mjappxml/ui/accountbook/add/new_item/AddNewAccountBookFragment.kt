package com.example.mjappxml.ui.accountbook.add.new_item

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.communication.util.removeNumberFormat
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentAddNewAccountBookBinding
import com.example.mjappxml.ui.accountbook.add.WhereToUseAdapter
import com.example.mjappxml.ui.dialog.DateSelectDialog
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewAccountBookFragment :
    BaseViewModelFragment<FragmentAddNewAccountBookBinding, AddNewAccountBookViewModel>(R.layout.fragment_add_new_account_book) {

    override val viewModel: AddNewAccountBookViewModel by viewModels()
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
        adapter.updateIsIncome(true)
    }

    fun updateExpenditure() {
        viewModel.updateIsIncome(false)
        adapter.updateIsIncome(false)
    }

    fun doRegister() {
        runCatching {
            viewModel.insertNewAccountBookItem(
                binding.editAmount.text.toString().removeNumberFormat().toInt()
            )
        }
    }

    fun showDateSelectDialog() {
        DateSelectDialog{
            viewModel.updateDate(it)
        }.also {
            it.setPrimaryColor(
                if (viewModel.isIncome.value) R.color.turquoise else R.color.red
            )
            it.show(parentFragmentManager, viewModel.item.value.date)
        }
    }
}