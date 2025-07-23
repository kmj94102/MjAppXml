package com.example.mjappxml.ui.accountbook.add.fixed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellFixedItemBinding
import com.example.mjappxml.model.FixedAccountBookInfo

class FixedAccountBookAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<FixedAccountBookInfo, FixedAccountBookViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixedAccountBookViewHolder(
            CellFixedItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cell_fixed_item, parent, false
                )
            )
        )

    override fun onBindViewHolder(holder: FixedAccountBookViewHolder, position: Int) {
        holder.bind(
            item = currentList[position],
            onClick = onClick
        )
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FixedAccountBookInfo>() {
            override fun areItemsTheSame(
                oldItem: FixedAccountBookInfo,
                newItem: FixedAccountBookInfo
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FixedAccountBookInfo,
                newItem: FixedAccountBookInfo
            ) = oldItem == newItem
        }
    }
}

class FixedAccountBookViewHolder(private val binding: CellFixedItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: FixedAccountBookInfo,
        onClick: (Int) -> Unit
    ) {
        binding.item = item
        binding.root.setOnClickListener { onClick(item.id) }
    }
}