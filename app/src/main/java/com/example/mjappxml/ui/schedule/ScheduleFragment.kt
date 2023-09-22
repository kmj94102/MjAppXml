package com.example.mjappxml.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mjappxml.BaseViewModelFragment
import com.example.mjappxml.R
import com.example.mjappxml.databinding.FragmentScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment :
    BaseViewModelFragment<FragmentScheduleBinding, ScheduleViewModel>(R.layout.fragment_schedule) {

    override val viewModel: ScheduleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.vm = viewModel
        initViews()
    }

    fun initViews() {
        binding.calendar.setOnChangeListener {
            viewModel.updateSelectDate(it)
        }
    }

    fun updateIsCalendar(view: View) {
        when(view.id) {
            binding.btnCalendar.id -> viewModel.updateIsCalendar(true)
            else -> viewModel.updateIsCalendar(false)
        }
    }

}