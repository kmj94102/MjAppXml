package com.example.mjappxml.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getToday(format: String = "yyyy.MM.dd"): String {
    val today = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format, Locale.KOREA)

    return formatter.format(today)
}

fun Calendar.toStringFormat(): String {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    return formatter.format(time)
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

fun getNextMonth(dateString: String, format: String = "yyyy.MM.dd"): String {
    val format = SimpleDateFormat(format, Locale.getDefault())
    val date = format.parse(dateString) ?: return ""
    val calendar = Calendar.getInstance()

    calendar.time = date
    calendar.add(Calendar.MONTH, 1)
    return format.format(calendar.time)
}

fun getPrevMonth(dateString: String, format: String = "yyyy.MM.dd"): String {
    val format = SimpleDateFormat(format, Locale.getDefault())
    val date = format.parse(dateString) ?: return ""
    val calendar = Calendar.getInstance()

    calendar.time = date
    calendar.add(Calendar.MONTH, -1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return format.format(calendar.time)
}

fun convertStringToDate(dateString: String, format: String = "yyyy.MM.dd"): Date {
    val dateFormat = SimpleDateFormat(format, Locale.KOREA)
    return dateFormat.parse(dateString) ?: Date()
}

fun getDayOfWeek(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date

    // 요일 정보 반환
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "일"
        Calendar.MONDAY -> "월"
        Calendar.TUESDAY -> "화"
        Calendar.WEDNESDAY -> "수"
        Calendar.THURSDAY -> "목"
        Calendar.FRIDAY -> "금"
        Calendar.SATURDAY -> "토"
        else -> ""
    }
}