package com.example.mjappxml.common

import android.content.res.ColorStateList
import android.graphics.Paint
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.communication.model.CalendarItem
import com.example.mjappxml.R

fun LinearLayout.addTaskInfoItems(
    items: List<CalendarItem.TaskInfo>,
    checkedChangeListener: (Int, CalendarItem.TaskInfo) -> Unit
) {
    removeAllViews()

    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked), // 체크 상태
            intArrayOf(-android.R.attr.state_checked) // 체크 안된 상태
        ),
        intArrayOf(
            ContextCompat.getColor(context, R.color.gray),
            ContextCompat.getColor(context, R.color.white)
        )
    )

    items.forEachIndexed { index, taskInfo ->
        val checkBox = CheckBox(context).apply {
            if (taskInfo.isCompleted) { isChecked = true }
            text = taskInfo.contents
            setTextColor(colorStateList)
            buttonTintList = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.dark_blue)
            )


            setOnCheckedChangeListener { _, isChecked ->
                checkedChangeListener(index, taskInfo.copy(isCompleted = isChecked))
                paintFlags = if (isChecked) {
                    paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }

        addView(checkBox)
    }
}