package com.example.mjappxml.ui.accountbook.add.new_item

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentAddNewAccountBookBinding
import com.example.mjappxml.ui.accountbook.add.WhereToUseAdapter
import com.example.mjappxml.ui.dialog.DateSelectDialog
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
        binding.recyclerView.layoutManager = object : GridLayoutManager(this.context, 4) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    fun updateIncome() {
        viewModel.updateIsIncome(true)
    }

    fun updateExpenditure() {
        viewModel.updateIsIncome(false)
    }

    fun doRegister() {
        viewModel.insertNewAccountBookItem(binding.editAmount.text.toString())
    }

    fun showDateSelectDialog() {
        DateSelectDialog{
            viewModel.updateDate(it)
        }.also {
            it.setPrimaryColor(
                if (viewModel.item.value.isIncome) R.color.turquoise else R.color.red
            )
            it.show(parentFragmentManager, viewModel.item.value.date)
        }
    }
}