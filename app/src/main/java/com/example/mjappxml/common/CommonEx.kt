package com.example.mjappxml.common

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager
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

fun Context.dialogResize(dialog: Dialog?){

    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val deviceWidth = if (Build.VERSION.SDK_INT < 30){
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        size.x
    } else {
        val rect = windowManager.currentWindowMetrics.bounds
        rect.width()
    }

    val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
    params?.width = (deviceWidth * 0.9).toInt()
    dialog?.window?.attributes = params as WindowManager.LayoutParams
}