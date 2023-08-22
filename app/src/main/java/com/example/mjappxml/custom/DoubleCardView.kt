package com.example.mjappxml.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.databinding.CustomDoubleCardViewBinding
import com.google.android.material.card.MaterialCardView

class DoubleCardView : MaterialCardView {

    private val binding by lazy {
        CustomDoubleCardViewBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_double_card_view, this, false)
        )
    }

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initViews()
        getAttrs(attributeSet)
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
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.DoubleCardView)
        attributeSetting(typeArray)
    }

    private fun attributeSetting(typedArray: TypedArray) {
        val radius = typedArray.getInteger(R.styleable.DoubleCardView_doubleCardRadius, 10)
        val topCardColor = typedArray.getColor(
            R.styleable.DoubleCardView_topCardColor,
            ContextCompat.getColor(context, R.color.white)
        )
        val bottomCardColor = typedArray.getColor(
            R.styleable.DoubleCardView_bottomCardColor,
            ContextCompat.getColor(context, R.color.white)
        )

        binding.run {
            topCard.radius = context.dpToPx(radius).toFloat()
            bottomCard.radius = context.dpToPx(radius).toFloat()
            this@DoubleCardView.radius = context.dpToPx(radius).toFloat()

            topCard.setCardBackgroundColor(topCardColor)
            bottomCard.setCardBackgroundColor(bottomCardColor)
        }

        with(binding) {
            topCard.radius = context.dpToPx(radius).toFloat()
            bottomCard.radius = context.dpToPx(radius).toFloat()

            topCard.setCardBackgroundColor(topCardColor)
            bottomCard.setCardBackgroundColor(bottomCardColor)
        }

        typedArray.recycle()
    }

    private fun initViews() {
        addView(binding.root)
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        strokeWidth = 0
    }

    fun setTopCardColor(@ColorRes color: Int) {
        binding.topCard.setCardBackgroundColor(ContextCompat.getColor(context, color))
    }

}