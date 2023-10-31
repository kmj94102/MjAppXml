package com.example.mjappxml.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.communication.model.MyCalendar
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.custom.CustomCalendar
import com.example.mjappxml.custom.IconButton
import com.example.mjappxml.ui.accountbook.IncomeExpenditureType

@BindingAdapter("stateRes")
fun setIconButtonImage(iconButton: IconButton, stateRes: Drawable) {
    iconButton.setImageDrawable(stateRes)
}

@BindingAdapter("pokemonWeekList")
fun setPokemonWeekList(layout: LinearLayout, weekList: List<Int>) {
    layout.removeAllViews()
    weekList.forEach { imageRes ->
        val imageView = ImageView(layout.context).also {
            val layoutParams = LinearLayout.LayoutParams(
                layout.context.dpToPx(30),
                layout.context.dpToPx(30)
            )
            layoutParams.marginStart = layout.context.dpToPx(4)
            it.layoutParams = layoutParams
            it.setImageResource(imageRes)
        }
        layout.addView(imageView)
    }
}

@BindingAdapter("dateTextColor")
fun setDateTextColor(textView: TextView, item: MyCalendar) {
    val color = when {
        item.isHoliday || item.dayOfWeek == "일" -> R.color.red
        item.dayOfWeek == "토" -> R.color.blue
        else -> R.color.black
    }
    textView.setTextColor(ContextCompat.getColor(textView.context, color))
    textView.alpha = if (item.itemList.size > 0) 1f else 0.3f
}

@BindingAdapter("calendarInfoList")
fun setCalendarInfoList(calendarView: CustomCalendar, list: List<MyCalendar>) {
    calendarView.setList(list)
}

@BindingAdapter("selectDate")
fun updateSelectDate(calendarView: CustomCalendar, selectDate: String) {
    calendarView.updateSelectDate(selectDate)
}

@BindingAdapter("incomeExpenditureIcon")
fun setIncomeExpenditureIcon(button: IconButton, type: String) {
   button.setImageRes(IncomeExpenditureType.getImageByType(type))
}