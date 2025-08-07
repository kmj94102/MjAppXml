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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :
    BaseViewModelFragment<FragmentScheduleBinding, ScheduleViewModel>(R.layout.fragment_schedule) {

    override val viewModel: ScheduleViewModel by viewModels()
    val adapter = ScheduleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
        initViews()
    }

    fun initViews() {
        with(binding.calendar) {
            setOnChangeListener(viewModel::updateSelectDate)
            setOnPrevMonthClickListener(viewModel::updatePrevMonth)
            setOnNextMonthClickListener(viewModel::updateNextMonth)
        }
    }

    fun goToAdd() {
        val bundle = bundleOf(Constants.StartDate to viewModel.selectDate.value)
        (activity as? MainActivity)?.goToPage(R.id.navigation_add_schedule, bundle)
    }

}