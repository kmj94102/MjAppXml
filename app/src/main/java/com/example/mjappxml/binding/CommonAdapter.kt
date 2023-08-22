package com.example.mjappxml.binding

import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.mjappxml.common.dpToPx
import com.google.android.material.card.MaterialCardView
import com.example.mjappxml.R
import com.example.mjappxml.common.spToPx

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