package com.example.mjappxml.ui.schedule

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.databinding.FragmentScheduleBinding
import com.example.mjappxml.ui.dialog.YearMonthSelectDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :
    BaseViewModelFragment<FragmentScheduleBinding, ScheduleViewModel>(R.layout.fragment_schedule) {

    override val viewModel: ScheduleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
        binding.adapter = ScheduleAdapter()
        initViews()
    }

    fun initViews() {
        binding.calendar.setOnChangeListener(viewModel::updateSelectDate)
    }

    fun updateIsCalendar(view: View) {
        when(view.id) {
            binding.btnCalendar.id -> viewModel.updateIsCalendar(true)
            else -> viewModel.updateIsCalendar(false)
        }
    }

    fun updateYearMonth() {
        YearMonthSelectDialog(viewModel::updateYearMonth)
            .show(parentFragmentManager, viewModel.selectDate.value)
    }

    fun goToAdd() {
        val bundle = bundleOf(Constants.StartDate to viewModel.selectDate.value)
        (activity as? MainActivity)?.goToPage(R.id.navigation_add_schedule, bundle)
    }

}