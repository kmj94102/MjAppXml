package com.example.mjappxml.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View.OnFocusChangeListener
import com.example.mjappxml.common.dpToPx
import com.google.android.material.textfield.TextInputEditText

class CustomEditTextView : TextInputEditText {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initView()
    }

    private fun initView() {
        setBackgroundResource(android.R.color.transparent)
        setPadding(getPadding(5), getPadding(3), getPadding(3), getPadding(0))
        compoundDrawablePadding = context.dpToPx(10)
        gravity = Gravity.CENTER_VERTICAL

        onFocusChangeListener = OnFocusChangeListener { _ , isFocused ->
            when(isFocused) {
                true -> {}
                false -> {}
            }
        }
    }

    private fun getPadding(dpSize: Int) : Int =
        context.dpToPx(dpSize)

    @SuppressLint("ClickableViewAccessibility")
    fun setDrawableClickListener(drawablePosition: Int, listener: () -> Unit) {
        setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) { z
                if (motionEvent.rawX >= (right - compoundDrawables[drawablePosition].bounds.width())) {
                    listener()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }

}