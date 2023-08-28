package com.example.mjappxml.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.custom.IconButton

@BindingAdapter("stateRes")
fun setIconButtonImage(iconButton: IconButton, stateRes: Drawable) {
    iconButton.setImageDrawable(stateRes)
}

@BindingAdapter("pokemonWeekList")
fun setPokemonWeekList(layout: LinearLayout, weekList: List<Int>) {
    layout.removeAllViews()
    weekList.forEach { imageRes ->
        val imageView = ImageView(layout.context).also {
            val layoutParams = LinearLayout.LayoutParams(
                layout.context.dpToPx(30),
                layout.context.dpToPx(30)
            )
            layoutParams.marginStart = layout.context.dpToPx(4)
            it.layoutParams = layoutParams
            it.setImageResource(imageRes)
        }
        layout.addView(imageView)
    }
}