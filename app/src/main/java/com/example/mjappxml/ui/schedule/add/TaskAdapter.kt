package com.example.mjappxml.ui.schedule.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.TaskItem
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CellTaskBinding

class TaskAdapter(
    private val onRemoveClick: (Int) -> Unit
) : ListAdapter<TaskItem, TaskViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(
            CellTaskBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.cell_task, parent, false)
            )
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(
            item = currentList[position],
            onRemoveClick = onRemoveClick
        )
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TaskItem>() {
            override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem) =
                oldItem.contents == newItem.contents

            override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem) =
                oldItem == newItem
        }
    }
}

class TaskViewHolder(private val binding: CellTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: TaskItem,
        onRemoveClick: (Int) -> Unit
    ) {
        binding.task = item
        binding.buttonRemove.setOnClickListener { onRemoveClick(absoluteAdapterPosition) }
    }
}