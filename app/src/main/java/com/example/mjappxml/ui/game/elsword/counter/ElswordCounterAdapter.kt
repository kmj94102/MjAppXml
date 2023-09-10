package com.example.mjappxml.ui.game.elsword.counter

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.communication.model.ElswordCharacter
import com.example.mjappxml.R
import com.example.mjappxml.binding.applyGrayScaleFilter
import com.example.mjappxml.databinding.CellElswordQuestBinding
import com.example.mjappxml.model.ElswordCharacters

class ElswordCounterAdapter(
    private val onItemClick: (ElswordCharacter) -> Unit
) : RecyclerView.Adapter<ElswordCounterViewHolder>() {
    private var groupedCharacters: Map<String, List<ElswordCharacter>> = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ElswordCounterViewHolder(
            CellElswordQuestBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.cell_elsword_quest, parent, false
                ),
            ),
            onItemClick
        )

    override fun onBindViewHolder(holder: ElswordCounterViewHolder, position: Int) {
        val key = groupedCharacters.keys.elementAt(position)
        groupedCharacters[key]?.let { holder.bind(key, it) }
    }

    override fun getItemCount() = groupedCharacters.keys.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list : List<ElswordCharacter>) {
        groupedCharacters = list.groupBy { it.group }
        notifyDataSetChanged()
    }

}

class ElswordCounterViewHolder(
    private val binding: CellElswordQuestBinding,
    private val onItemClick: (ElswordCharacter) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(key: String, list: List<ElswordCharacter>) {
        val color = ElswordCharacters.getCharacterColor(key)
        binding.doubleCard.setBottomCardColor(color)
        val viewList = listOf(
            binding.imgLine1,
            binding.imgLine2,
            binding.imgLine3,
            binding.imgLine4
        )
        val progressTextList = listOf(
            binding.txtProgress1,
            binding.txtProgress2,
            binding.txtProgress3,
            binding.txtProgress4,
        )

        viewList.forEachIndexed { index, imageView ->
            runCatching {
                imageView.load(list[index].image) {
                    crossfade(true)
                    applyGrayScaleFilter(imageView, list[index].isComplete.not())
                }

                progressTextList[index].isVisible = list[index].isOngoing
                if (list[index].isOngoing) {
                    progressTextList[index].setBackgroundColor(Color.parseColor(color))
                    ObjectAnimator
                        .ofFloat(progressTextList[index], "alpha", 0.8f, 0.3f)
                        .also {
                            it.duration = 1000
                            it.repeatMode = ValueAnimator.REVERSE
                            it.repeatCount = ValueAnimator.INFINITE
                        }
                        .start()
                }

                imageView.setOnClickListener { onItemClick(list[index]) }
            }
        }
    }
}