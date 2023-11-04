package com.example.mjappxml.ui.accountbook.add.fixed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.FixedAccountBook
import com.example.communication.util.removeNumberFormat
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellFixedItemBinding

class FixedAccountBookAdapter(
    private val onDateClick: (String, Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onRegisterClick: (FixedAccountBook) -> Unit
) : ListAdapter<FixedAccountBook, FixedAccountBookViewHolder>(diffUtil) {
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
            onDateClick = { onDateClick(it, position) },
            onDeleteClick = onDeleteClick,
            onRegisterClick = onRegisterClick
        )
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FixedAccountBook>() {
            override fun areItemsTheSame(
                oldItem: FixedAccountBook,
                newItem: FixedAccountBook
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FixedAccountBook,
                newItem: FixedAccountBook
            ) = oldItem == newItem
        }
    }
}

class FixedAccountBookViewHolder(private val binding: CellFixedItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: FixedAccountBook,
        onDateClick: (String) -> Unit,
        onDeleteClick: (Int) -> Unit,
        onRegisterClick: (FixedAccountBook) -> Unit
    ) {
        binding.item = item
        binding.txtDay.setOnClickListener {
            onDateClick(item.date)
        }
        binding.cardDelete.setOnClickListener {
            onDeleteClick(item.id)
        }
        binding.btnRegister.setOnClickListener {
            runCatching {
                val amount = binding.editAmount.text.toString().removeNumberFormat().toInt()
                onRegisterClick(item.copy(amount = amount))
            }
        }
    }
}