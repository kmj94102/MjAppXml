package com.example.mjappxml.ui.schedule.add

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.common.getToday
import com.example.mjappxml.databinding.FragmentAddPlanBinding
import com.example.mjappxml.ui.dialog.DateSelectDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlanFragment :
    BaseViewModelFragment<FragmentAddPlanBinding, AddPlanViewModel>(R.layout.fragment_add_plan) {

    override val viewModel: AddPlanViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            vm = viewModel
            fragment = this@AddPlanFragment
            adapter = TaskAdapter(viewModel::removeTaskItem)
        }

        arguments?.getString(Constants.StartDate)?.let {
            viewModel.updateDate(it)
        } ?: viewModel.updateDate(getToday())

    }

    fun updateDate() {
        DateSelectDialog {
            viewModel.updateDate(it)
        }.show(parentFragmentManager, viewModel.item.value.planDate)
    }

    fun goToSchedule() {
        (activity as? MainActivity)?.goToPage(
            R.id.action_add_schedule,
            bundleOf(
                Constants.StartDate to viewModel.item.value.planDate
            )
        )
    }

}