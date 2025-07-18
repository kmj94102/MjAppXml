package com.example.communication.util

import java.text.NumberFormat
import kotlin.math.abs

fun Int.formatAmount(): String = NumberFormat.getNumberInstance().format(this)

fun String.removeNumberFormat() =
    replace(",", "").replace("만", "").replace("원", "").trim()


fun Int.formatAmountWithSign(): String  = formatAmount().plus(" 원")

fun Int.formatAmountInTenThousand(): String {
    val format = (abs(this) / 10_000).formatAmount()

    return when {
        this > 0 -> "+ $format 만원"
        this < 0 -> "- $format 만원"
        else -> format.plus(" 원")
    }
}