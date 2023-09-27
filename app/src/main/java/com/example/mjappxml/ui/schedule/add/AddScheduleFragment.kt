package com.example.mjappxml.ui.schedule.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.common.Constants
import com.example.mjappxml.common.getToday
import com.example.mjappxml.databinding.FragmentAddScheduleBinding
import com.example.mjappxml.ui.dialog.DateSelectDialog
import com.example.mjappxml.ui.dialog.TimeSelectDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddScheduleFragment :
    BaseViewModelFragment<FragmentAddScheduleBinding, AddScheduleViewModel>(R.layout.fragment_add_schedule) {

    override val viewModel: AddScheduleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.fragment = this

        arguments?.getString(Constants.StartDate)?.let {
            viewModel.updateDate(it)
        } ?: viewModel.updateDate(getToday())
    }

    fun insertSchedule() {
        viewModel.insertSchedule()
    }

    fun onDateSelect() {
        DateSelectDialog {
            viewModel.updateDate(it)
        }.show(parentFragmentManager, viewModel.item.value.date)
    }

    fun onRepeatEndDateSelect() {
        DateSelectDialog {
            viewModel.updateRepeatEndDateSelect(it)
        }.show(parentFragmentManager, viewModel.item.value.date)
    }

    fun onStartTimeSelect() {
        TimeSelectDialog {
            viewModel.updateStartTime(it)
        }.show(parentFragmentManager, viewModel.item.value.startTime)
    }

    fun onEndTimeSelect() {
        TimeSelectDialog {
            viewModel.updateEndTime(it)
        }.show(parentFragmentManager, viewModel.item.value.endTime)
    }

}