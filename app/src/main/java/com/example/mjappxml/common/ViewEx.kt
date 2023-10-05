package com.example.mjappxml.common

import android.graphics.Paint
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.communication.model.CalendarItem
import com.example.mjappxml.R

fun CheckBox.setStrikeThruText() {
    paintFlags = when (isChecked) {
        true -> {
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        false -> {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}

fun LinearLayout.addTaskInfoItems(
    items: List<CalendarItem.TaskInfo>,
    checkedChangeListener: (Int, CalendarItem.TaskInfo) -> Unit
) {
    removeAllViews()
    items.forEachIndexed { index, taskInfo ->
        val checkBox = CheckBox(context).also {
            if (taskInfo.isCompleted) { it.isChecked = true }
            it.text = taskInfo.contents
            it.setStrikeThruText()
            it.setOnCheckedChangeListener { _, isChecked ->
                checkedChangeListener(index, taskInfo.copy(isCompleted = isChecked))
            }
        }

        addView(checkBox)
    }
}