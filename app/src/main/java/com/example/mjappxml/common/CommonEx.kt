package com.example.mjappxml.common

import android.content.Context
import android.widget.Toast
import com.example.communication.model.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun Context.dpToPx(dpSize: Int): Int =
    dpSize * resources.displayMetrics.density.toInt()

fun Context.spToPx(spSize: Int): Int =
    spSize * resources.displayMetrics.scaledDensity.toInt()

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T> Flow<T>.customCatch(
    onNetworkError: () -> Unit,
    onError: (String?) -> Unit
) = catch {
    if (it is NetworkError) { onNetworkError() }
    else { onError(it.message) }
}