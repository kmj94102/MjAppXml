package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.communication.model.MyCalendar
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.databinding.CustomCalendarItemBinding

class CustomCalendarItem : LinearLayout {

    private val binding = CustomCalendarItemBinding.inflate(LayoutInflater.from(context))
    private var item = MyCalendar()

    @ColorInt
    private var primaryColor: Int = ContextCompat.getColor(context, R.color.purple)

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initViews()
    }

    private fun initViews() {
        addView(binding.root)
        gravity = Gravity.CENTER
        binding.cardView.layoutParams =
            LayoutParams(context.dpToPx(35), context.dpToPx(35))
    }

    fun setItem(item: MyCalendar) {
        this.item = item
        binding.item = item
        binding.invalidateAll()
    }

    fun setOnclickListener(listener: (String) -> Unit) {
        if (item.date.isNotEmpty()) {
            binding.cardView.setOnClickListener {
                listener(item.detailDate)
            }
        }
    }

    fun setPrimaryColor(@ColorInt primaryColor: Int) {
        this.primaryColor = primaryColor
    }

    fun updateSelect(isSelect: Boolean) {
        binding.cardView.strokeWidth = if (isSelect) context.dpToPx(1) else 0
    }

    fun updateIsToday(@ColorInt color: Int) {
        binding.cardView.strokeColor = color
    }
}