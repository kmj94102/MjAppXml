package com.example.communication.model

import com.example.communication.util.formatAmountWithSign
import java.text.SimpleDateFormat
import java.util.*

/**
 * 달력 아이템
 * @param date 날짜
 * @param isHoliday 공휴일 여부
 * @param isSpecialDay 특별일 여부
 * @param dateInfo 날짜 정보 (ex : 추석, 설날)
 * @param dayOfWeek 요일
 * @param detailDate 상세 날짜
 * @param itemList 일정 리스트
 * **/
data class MyCalendar(
    val date: String = "",
    val isHoliday: Boolean = false,
    val isSpecialDay: Boolean = false,
    val dateInfo: String = "",
    val dayOfWeek: String = "",
    val detailDate: String = "",
    val itemList: MutableList<CalendarItem> = mutableListOf(),
) {
    fun getYearMonth() = runCatching { detailDate.substring(0, 7) }.getOrDefault("")
    fun getDateAndDayOfWeek() = "${date.padStart(2, '0')}(${dayOfWeek})"
}

/** 달력 정보 생성 **/
fun fetchMyCalendarByMonth(
    year: Int,
    month: Int
): List<MyCalendar> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDay = calendar.get(Calendar.DAY_OF_WEEK)

    val lastDayIndex = firstDay + monthDays - 2
    val lastIndex = if (lastDayIndex < 35) 34 else 41

    return (0..lastIndex)
        .map {
            if (it < firstDay - 1 || it > lastDayIndex) {
                MyCalendar()
            } else {
                val date = (it - firstDay + 2).toString()
                MyCalendar(
                    date = date,
                    dayOfWeek = getDayOfWeek(it),
                    detailDate = getDetailDate(year, month, date)
                )
            }
        }
}

fun fetchMyCalendarByDate(
    startDate: String,
    endDate: String,
    list: List<CalendarItem.AccountHistoryInfo>
): List<MyCalendar> {
    val sdfInput = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val sdfOutput = SimpleDateFormat("dd", Locale.getDefault())

    val startCalendar = Calendar.getInstance()
    val endCalendar = Calendar.getInstance()
    runCatching {
        startCalendar.time = sdfInput.parse(startDate) ?: return emptyList()
        endCalendar.time = sdfInput.parse(endDate) ?: return emptyList()
    }.onFailure {
        return emptyList()
    }

    val calendarList = mutableListOf<MyCalendar>()

    val dayOfWeek = startCalendar.get(Calendar.DAY_OF_WEEK)
    val emptyDays = if (dayOfWeek == Calendar.SUNDAY) 0 else dayOfWeek - 1

    repeat(emptyDays) {
        calendarList.add(MyCalendar())
    }

    while (startCalendar <= endCalendar) {
        val date = sdfOutput.format(startCalendar.time)
        val todayOfWeek = getDayOfWeek(startCalendar.get(Calendar.DAY_OF_WEEK))
        val detailDate = getDetailDate(
            startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH) + 1,
            startCalendar.get(Calendar.DAY_OF_MONTH).toString()
        )

        val itemList = list.filter { it.date == detailDate }

        val myCalendar = MyCalendar(
            date = date,
            dayOfWeek = todayOfWeek,
            detailDate = detailDate,
            itemList = itemList.toMutableList()
        )

        calendarList.add(myCalendar)
        startCalendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return calendarList
}

private fun getDayOfWeek(index: Int): String {
    val list = listOf("일", "월", "화", "수", "목", "금", "토")
    return list[index % 7]
}

private fun getDetailDate(year: Int, month: Int, date: String) =
    "$year.${month.toString().padStart(2, '0')}.${date.padStart(2, '0')}"

fun getWeeklyDateRange(dateString: String): List<CalendarResult> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val startDayOfWeek = getStartDayOfWeek(dateString) ?: return emptyList()
    val lastDayOfWeek = getLastDayOfWeek(dateString) ?: return emptyList()

    val list = mutableListOf<CalendarResult>()

    var currentDate = startDayOfWeek.time
    while (!currentDate.after(lastDayOfWeek.time)) {
        list.add(
            CalendarResult(
                date = dateFormat.format(currentDate),
            )
        )
        startDayOfWeek.add(Calendar.DAY_OF_YEAR, 1)
        currentDate = startDayOfWeek.time
    }

    return list
}

fun getStartDayOfWeek(dateString: String): Calendar? {
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val date = format.parse(dateString) ?: return null

    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    return calendar
}

fun getLastDayOfWeek(dateString: String): Calendar? {
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val date = format.parse(dateString) ?: return null

    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
    return calendar
}

data class CalendarResult(
    val date: String = "",
    val calendarInfoList: List<CalendarInfo> = listOf(),
    val scheduleInfoList: List<CalendarItem.ScheduleInfo> = listOf(),
    val planInfoList: List<CalendarItem.PlanInfo> = listOf()
) {
    fun toMyCalendarInfo(): MyCalendarInfo {
        val isHoliday = calendarInfoList.any { it.isHoliday }
        val isSpecialDay = calendarInfoList.any { it.isSpecialDay }
        val info = if (calendarInfoList.isNotEmpty()) {
            calendarInfoList.map { it.info }.reduce { acc, s -> "$acc, $s" }
        } else ""
        scheduleInfoList.filter { it.id == 0 }

        return MyCalendarInfo(
            date = date.replace("-", "."),
            info = info,
            isHoliday = isHoliday,
            isSpecialDay = isSpecialDay,
            list = scheduleInfoList + planInfoList
        )
    }
}

data class MyCalendarInfo(
    val date: String = "",
    val info: String = "",
    val isHoliday: Boolean = false,
    val isSpecialDay: Boolean = false,
    val list: List<CalendarItem> = listOf()
)

data class CalendarInfo(
    val id: Int,
    val info: String,
    val isHoliday: Boolean,
    val isSpecialDay: Boolean
)

sealed class CalendarItem(val type: String) {

    data class ScheduleInfo(
        val id: Int,
        val startTime: String,
        val endTime: String,
        val recurrenceType: String,
        val recurrenceEndDate: String?,
        val scheduleContent: String,
        val scheduleTitle: String,
        val recurrenceId: Int?
    ) : CalendarItem(SCHEDULE) {
        fun getTime() = "${startTime.substring(11, 16)} ~ ${endTime.substring(11, 16)}"
    }

    data class PlanInfo(
        val id: Int,
        val title: String,
        val taskList: List<TaskInfo>
    ) : CalendarItem(PLAN)

    data class TaskInfo(
        val id: Int,
        val contents: String,
        val isCompleted: Boolean
    )

    data class AccountHistoryInfo(
        val id: Int = 0,
        val date: String,
        val dateOfWeek: String,
        val amount: Int,
        val usageType: String,
        val whereToUse: String
    ) : CalendarItem(HISTORY) {
        fun getFormatAmount() = amount.formatAmountWithSign()
    }

    companion object {
        const val SCHEDULE = "schedule"
        const val PLAN = "plan"
        const val HISTORY = "history"
    }
}
