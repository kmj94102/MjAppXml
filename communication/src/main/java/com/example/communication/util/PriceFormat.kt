package com.example.communication.util

import java.text.NumberFormat

fun Int.formatAmount(): String = NumberFormat.getNumberInstance().format(this)

fun String.removeNumberFormat() =
    replace(",", "").replace("만", "").replace("원", "").replace(" ", "").trim()

fun Int.formatAmountWithSign(): String = formatAmount().plus(" 원")

fun String.updateNumberFormatEditText(): String {
    return runCatching { removeNumberFormat().toInt() }
        .getOrDefault(Int.MAX_VALUE)
        .formatAmountWithSign()
}