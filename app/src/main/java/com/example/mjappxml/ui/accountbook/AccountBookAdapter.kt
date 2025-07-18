package com.example.mjappxml.ui.accountbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mjappxml.databinding.CellAccountBookDateBinding
import com.example.mjappxml.databinding.CellAccountBookItemBinding
import com.example.mjappxml.model.AccountBookInfo

class AccountBookAdapter : ListAdapter<AccountBookInfo, ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when(viewType) {
            AccountBookInfo.ITEM -> AccountBookViewHolder(
                CellAccountBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> AccountBookDateViewHolder(
                CellAccountBookDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is AccountBookViewHolder ->
                holder.bind(currentList[position] as AccountBookInfo.Item)
            is AccountBookDateViewHolder ->
                holder.bind(currentList[position] as AccountBookInfo.Date)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is AccountBookInfo.Item -> AccountBookInfo.ITEM
            is AccountBookInfo.Date -> AccountBookInfo.DATE
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AccountBookInfo>() {
            override fun areItemsTheSame(oldItem: AccountBookInfo, newItem: AccountBookInfo) =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: AccountBookInfo,
                newItem: AccountBookInfo
            ) = oldItem == newItem

        }
    }
}

class AccountBookViewHolder(
    private val binding: CellAccountBookItemBinding
): ViewHolder(binding.root) {
    fun bind(item: AccountBookInfo.Item) {
        binding.item = item
    }
}

class AccountBookDateViewHolder(
    private val binding: CellAccountBookDateBinding
): ViewHolder(binding.root) {
    fun bind(item: AccountBookInfo.Date) {
        binding.item = item
    }
}