package com.example.mjappxml.ui.accountbook.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.CalendarItem
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellMonthHistoryBinding
import com.example.mjappxml.ui.accountbook.IncomeExpenditureType

class MonthHistoryAdapter: ListAdapter<CalendarItem, MonthHistoryViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MonthHistoryViewHolder(
            CellMonthHistoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.cell_month_history, parent, false)
            )
        )

    override fun onBindViewHolder(holder: MonthHistoryViewHolder, position: Int) {
        (currentList[position] as? CalendarItem.AccountHistoryInfo)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CalendarItem>() {
            override fun areItemsTheSame(
                oldItem: CalendarItem,
                newItem: CalendarItem
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CalendarItem,
                newItem: CalendarItem
            ) = oldItem == newItem
        }
    }
}

class MonthHistoryViewHolder(private val binding: CellMonthHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CalendarItem.AccountHistoryInfo) {
        binding.item = item
        binding.imageView.setImageResource(IncomeExpenditureType.getImageByType(item.usageType))
    }
}