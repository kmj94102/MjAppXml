package com.example.mjappxml.ui.game.elsword.counter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.communication.model.ElswordQuestSimple
import com.example.mjappxml.databinding.CellElsworedCounterProgressBinding
import com.example.mjappxml.R

class ElswordCounterProgressAdapter(private val onDelete: (Int) -> Unit) :
    ListAdapter<ElswordQuestSimple, ElswordCounterProgressViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ElswordCounterProgressViewHolder(
        CellElsworedCounterProgressBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_elswored_counter_progress, parent, false
            )
        )
    )

    override fun onBindViewHolder(holder: ElswordCounterProgressViewHolder, position: Int) {
        holder.bind(currentList[position], onDelete)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ElswordQuestSimple>() {
            override fun areItemsTheSame(
                oldItem: ElswordQuestSimple,
                newItem: ElswordQuestSimple
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ElswordQuestSimple,
                newItem: ElswordQuestSimple
            ) = oldItem == newItem
        }
    }
}

class ElswordCounterProgressViewHolder(
    private val binding: CellElsworedCounterProgressBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ElswordQuestSimple, onDelete: (Int) -> Unit) {
        binding.info = item
        binding.btnDelete.setOnClickListener { onDelete(item.id) }
    }
}