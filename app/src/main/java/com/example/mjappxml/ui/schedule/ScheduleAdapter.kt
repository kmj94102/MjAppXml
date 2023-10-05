package com.example.mjappxml.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.CalendarItem
import com.example.mjappxml.R
import com.example.mjappxml.common.addTaskInfoItems
import com.example.mjappxml.databinding.CellPlanBinding
import com.example.mjappxml.databinding.CellScheduleBinding

class ScheduleAdapter : ListAdapter<CalendarItem, RecyclerView.ViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is CalendarItem.ScheduleInfo -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> {
                ScheduleViewHolder(
                    CellScheduleBinding.bind(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.cell_schedule, parent, false)
                    )
                )
            }

            else -> {
                PlanViewHolder(
                    CellPlanBinding.bind(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.cell_plan, parent, false)
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ScheduleViewHolder -> {
                (currentList[position] as? CalendarItem.ScheduleInfo)?.let {
                    holder.bind(it)
                }
            }
            is PlanViewHolder -> {
                (currentList[position] as? CalendarItem.PlanInfo)?.let {
                    holder.bind(it)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CalendarItem>() {
            override fun areItemsTheSame(oldItem: CalendarItem, newItem: CalendarItem) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: CalendarItem, newItem: CalendarItem) =
                oldItem == newItem

        }
    }
}

class ScheduleViewHolder(
    private val binding: CellScheduleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CalendarItem.ScheduleInfo) {
        binding.item = item
    }
}

class PlanViewHolder(
    private val binding: CellPlanBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CalendarItem.PlanInfo) {
        binding.item = item
        binding.linearLayout.addTaskInfoItems(item.taskList) { index, info ->

        }
    }
}