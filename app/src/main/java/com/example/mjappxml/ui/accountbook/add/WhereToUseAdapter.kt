package com.example.mjappxml.ui.accountbook.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellWhereToUseBinding
import com.example.mjappxml.ui.accountbook.IncomeExpenditureType

class WhereToUseAdapter(
    private val onClick: (WhereToUse) -> Unit
) : ListAdapter<WhereToUse, WhereToUseViewHolder>(diffUtil) {

    private val _isIncome = MutableLiveData(true)
    val isIncome: LiveData<Boolean> = _isIncome

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WhereToUseViewHolder(
            CellWhereToUseBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cell_where_to_use, parent, false
                )
            ),
            parent.findViewTreeLifecycleOwner()
        )

    override fun onBindViewHolder(holder: WhereToUseViewHolder, position: Int) {
        holder.bind(currentList[position], this, onClick)
    }

    fun updateIsIncome(value: Boolean) {
        _isIncome.value = value
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
    private val lifecycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: WhereToUse,
        adapter: WhereToUseAdapter,
        onClick: (WhereToUse) -> Unit
    ) {
        binding.item = item
        binding.root.setOnClickListener { onClick(item) }
        lifecycleOwner?.let {
            adapter.isIncome.observe(it) { value ->
                val color =
                    if (item.isSelect && value) R.color.turquoise
                    else if (item.isSelect && !value) R.color.red
                    else R.color.white

                binding.cardView.setTopCardColor(color)
            }
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
        fun getWhereToUseList() = IncomeExpenditureType.values().map {
            WhereToUse(
                usageType = it.type,
                name = it.typeName,
                icon = it.imageRes,
                isSelect = it.typeName == IncomeExpenditureType.Meal.typeName
            )
        }
    }
}