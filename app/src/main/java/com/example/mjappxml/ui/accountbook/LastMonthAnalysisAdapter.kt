package com.example.mjappxml.ui.accountbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.LastMonthAnalysisItem
import com.example.communication.util.formatAmountWithSign
import com.example.mjappxml.databinding.CellLastMonthUsedItemBinding
import com.example.mjappxml.R

class LastMonthAnalysisAdapter : ListAdapter<LastMonthUsed, LastMonthAnalysisViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LastMonthAnalysisViewHolder(
            CellLastMonthUsedItemBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_last_month_used_item, parent, false)
            )
        )

    override fun onBindViewHolder(holder: LastMonthAnalysisViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<LastMonthUsed>() {
            override fun areItemsTheSame(oldItem: LastMonthUsed, newItem: LastMonthUsed) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: LastMonthUsed,
                newItem: LastMonthUsed
            ) = oldItem == newItem

        }
    }
}

class LastMonthAnalysisViewHolder(private val binding: CellLastMonthUsedItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LastMonthUsed) {
        binding.item = item
    }
}

data class LastMonthUsed(
    val amount: String,
    val whereToUse: String,
    val iconRes: Int,
    val isIncome: Boolean
)

fun LastMonthAnalysisItem.mapper() = LastMonthUsed(
    amount = amount.formatAmountWithSign(),
    whereToUse = IncomeExpenditureType.getNameByType(usageType),
    iconRes = IncomeExpenditureType.getImageByType(usageType),
    isIncome = amount >= 0
)