package com.example.communication.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getToday(format: String = "yyyy.MM.dd"): String {
    val today = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format, Locale.KOREA)

    return formatter.format(today)
}