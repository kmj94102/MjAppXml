package com.example.mjappxml.binding

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.communication.model.ThisYearSummaryItem
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.custom.DoubleCardView
import com.example.mjappxml.custom.MonthlyUseView
import com.google.android.material.chip.Chip
import com.google.android.material.progressindicator.LinearProgressIndicator

@BindingAdapter("imageRes")
fun imageLoad(imageView: ImageView, @DrawableRes resId: Int) {
    imageView.setImageResource(resId)
}

@BindingAdapter("startDrawable")
fun setTextStartDrawable(textView: TextView, @DrawableRes resId: Int) {
    val drawable = ContextCompat.getDrawable(textView.context, resId)
    val size = textView.context.dpToPx(27)
    drawable?.setBounds(0, 0, size, size)

    textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}

@BindingAdapter(value = ["imageAddress", "loadingRes"])
fun loadCoilImage(imageView: ImageView, imageAddress: String, loadingRes: Drawable) {
    imageView.load(imageAddress) {
        crossfade(true)
        placeholder(loadingRes)
    }
}

@BindingAdapter("isGrayScale")
fun applyGrayScaleFilter(imageView: ImageView, isGrayScale: Boolean) {
    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(if (isGrayScale) 0f else 1f)

    val colorFilter = ColorMatrixColorFilter(colorMatrix)
    imageView.colorFilter = colorFilter
}

@BindingAdapter("chipBackgroundHex")
fun setChipBackgroundColorHex(chip: Chip, chipBackgroundHex: Long) {
    val color = Color.argb(
        ((chipBackgroundHex shr 24) and 0xFF).toInt(),
        ((chipBackgroundHex shr 16) and 0xFF).toInt(),
        ((chipBackgroundHex shr 8) and 0xFF).toInt(),
        (chipBackgroundHex and 0xFF).toInt()
    )

    chip.chipBackgroundColor = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)),
        intArrayOf(color, color)
    )
}

@BindingAdapter("chipIconRes")
fun setChipIcon(chip: Chip, chipIconRes: Int) {
    chip.chipIcon = ContextCompat.getDrawable(chip.context, chipIconRes)
}

@BindingAdapter("animationProgress")
fun setAnimationProgress(progressIndicator: LinearProgressIndicator, progress: Float) {
    progressIndicator.setProgressCompat(progress.toInt(), true)
}

@BindingAdapter("pickerValues")
fun setPickerValues(picker: NumberPicker, array: Array<String>) {
    picker.also {
        it.minValue = 0
        it.maxValue = array.size - 1
        it.displayedValues = array
    }
}

@BindingAdapter("topCardRes")
fun setTopCardRes(cardView: DoubleCardView, @ColorInt color: Int) {
    cardView.setTopCardColorInt(color)
}

@BindingAdapter("monthlyUseItems")
fun setMonthlyUseItems(gridLayout: GridLayout, list: List<ThisYearSummaryItem>) {
    list.forEachIndexed { index, item ->
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.columnSpec = GridLayout.spec(index % 4, 1, 1f)
        if (index < 8) {
            layoutParams.bottomMargin = gridLayout.context.dpToPx(5)
        }

        val itemView = MonthlyUseView(gridLayout.context).also {
            it.setItem(item)
            it.layoutParams = layoutParams
        }
        gridLayout.addView(itemView)
    }
}