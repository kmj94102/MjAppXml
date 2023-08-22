package com.example.mjappxml.common

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dpSize: Int): Int =
    dpSize * resources.displayMetrics.density.toInt()

fun Context.spToPx(spSize: Int): Int =
    spSize * resources.displayMetrics.scaledDensity.toInt()
