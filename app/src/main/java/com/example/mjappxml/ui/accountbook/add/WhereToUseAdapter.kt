package com.example.mjappxml.ui.accountbook.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.databinding.CellWhereToUseBinding
import com.example.mjappxml.ui.accountbook.IncomeExpenditureType

class WhereToUseAdapter(
    private val onClick: (WhereToUse) -> Unit
) : ListAdapter<WhereToUse, WhereToUseViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WhereToUseViewHolder(
            CellWhereToUseBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cell_where_to_use, parent, false
                )
            )
        )

    override fun onBindViewHolder(holder: WhereToUseViewHolder, position: Int) {
        holder.bind(currentList[position], onClick)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<WhereToUse>() {
            override fun areItemsTheSame(oldItem: WhereToUse, newItem: WhereToUse) =
                oldItem.usageType == newItem.usageType

            override fun areContentsTheSame(oldItem: WhereToUse, newItem: WhereToUse) =
                oldItem == newItem
        }
    }
}

class WhereToUseViewHolder(
    private val binding: CellWhereToUseBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: WhereToUse,
        onClick: (WhereToUse) -> Unit
    ) {
        binding.item = item
        binding.root.setOnClickListener { onClick(item) }
        val color: Int =
            if (item.isSelect) R.color.dark_blue_ten
            else R.color.light_black

        binding.cardView.setCardBackgroundColor(
            ContextCompat.getColor(binding.cardView.context, color)
        )

        binding.cardView.strokeWidth = if (item.isSelect) {
            binding.cardView.context.dpToPx(1)
        } else {
            0
        }
    }
}

data class WhereToUse(
    val usageType: String,
    val name: String,
    val icon: Int,
    val isSelect: Boolean
) {
    companion object {
        fun getWhereToUseList() = IncomeExpenditureType.entries.map {
            WhereToUse(
                usageType = it.type,
                name = it.typeName,
                icon = it.imageRes,
                isSelect = it.typeName == IncomeExpenditureType.Meal.typeName
            )
        }
    }
}