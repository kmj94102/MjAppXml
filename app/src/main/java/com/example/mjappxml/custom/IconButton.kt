package com.example.mjappxml.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.google.android.material.card.MaterialCardView

class IconButton : MaterialCardView {

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        getAttrs(attributeSet)
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initViews()
        getAttrs(attributeSet)
    }

    private fun getAttrs(attributeSet: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.IconButton)
        attributeSetting(typeArray)
    }

    private fun initViews() {
        strokeWidth = context.dpToPx(1)
        strokeColor = ContextCompat.getColor(context, R.color.black)
    }

    private fun attributeSetting(typedArray: TypedArray) {
        val iconSize = typedArray.getDimension(
            R.styleable.IconButton_iconSize,
            context.resources.getDimension(R.dimen.icon_default_size)
        )
        val iconRes = typedArray.getResourceId(
            R.styleable.IconButton_res,
            R.drawable.ic_close
        )


        val icon = ImageView(context)
        addView(icon)

        val layoutPram = LayoutParams(iconSize.toInt(), iconSize.toInt())
        layoutPram.gravity = Gravity.CENTER
        icon.layoutParams = layoutPram
        icon.setImageResource(iconRes)

        typedArray.recycle()
    }

}