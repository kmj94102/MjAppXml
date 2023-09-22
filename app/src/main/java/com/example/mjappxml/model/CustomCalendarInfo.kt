package com.example.mjappxml.model
import com.example.communication.model.MyCalendarInfo
import com.example.mjappxml.common.convertStringToDate
import com.example.mjappxml.common.getDayOfWeek

data class CustomCalendarInfo(
    val date: String,
    val dayOfWeek: String,
    val detailDate: String,
    val isHoliday: Boolean,
    val isToday: Boolean,
    val isSelect: Boolean,
    val itemCount: Int
) {
    companion object {
        fun create() = CustomCalendarInfo(
            date = "",
            dayOfWeek = "",
            detailDate = "",
            isHoliday = false,
            isToday = false,
            isSelect = false,
            itemCount = 0
        )
    }
}

fun MyCalendarInfo.toCustomCalendarInfo(today: String, selectDate: String) = CustomCalendarInfo(
    date = runCatching { date.substring(8, 10) }.getOrElse { "" },
    dayOfWeek = getDayOfWeek(convertStringToDate(date)),
    detailDate = date,
    isHoliday = isHoliday,
    isToday = today == date,
    isSelect = selectDate == date,
    itemCount = list.size
)