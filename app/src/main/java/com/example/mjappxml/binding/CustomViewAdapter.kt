package com.example.mjappxml.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.databinding.BindingAdapter
import com.example.communication.model.MyCalendar
import com.example.communication.model.MyCalendarInfo
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.custom.CustomBottomNavigationItem
import com.example.mjappxml.custom.CustomCalendar
import com.example.mjappxml.custom.IconButton
import com.example.mjappxml.custom.WeekCalendarItem
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
        item.isHoliday || item.dayOfWeek == "일" -> "#FFA70303".toColorInt()
        item.dayOfWeek == "토" -> "#FF0869B3".toColorInt()
        else -> "#FFFAFAFA".toColorInt()
    }
    textView.setTextColor(color)
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

@BindingAdapter("navigationItem")
fun setNavigationItem(
    navigationItem: CustomBottomNavigationItem,
    item: CustomBottomNavigationItem.NavigationItem
) {
    navigationItem.setItem(item)
}

@BindingAdapter(value = ["weekItem", "weekToday"])
fun setWeekCalendarItem(calendar: WeekCalendarItem, weekItem: MyCalendarInfo, weekToday: String) {
    calendar.setCalendarInfo(
        info = weekItem,
        today = weekToday
    )
}

@BindingAdapter("weekSelectDate")
fun setWeekSelectItem(calendar: WeekCalendarItem, selectDate: String) {
    calendar.setSelect(selectDate)
}