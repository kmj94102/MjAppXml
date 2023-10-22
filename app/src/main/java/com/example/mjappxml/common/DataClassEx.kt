package com.example.mjappxml.common

import com.example.communication.model.ThisYearSummaryItem
import com.example.mjappxml.R

fun ThisYearSummaryItem.getCardColor() = when {
    info > 0 -> R.color.turquoise
    info < 0 -> R.color.red
    else ->  R.color.light_gray
}