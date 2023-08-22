package com.example.mjappxml.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CustomWeekCalendarItemBinding

class WeekCalendarItem : ConstraintLayout {

    private val binding = CustomWeekCalendarItemBinding.inflate(LayoutInflater.from(context))

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

    private fun initViews() {
        addView(binding.root)
        binding.root.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getAttrs(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WeekCalendarItem)
        attributeSetting(typedArray)
    }

    private fun attributeSetting(typedArray: TypedArray) {
        val isToday = typedArray.getBoolean(R.styleable.WeekCalendarItem_isToday, false)
        val isSelect = typedArray.getBoolean(R.styleable.WeekCalendarItem_isSelect, false)
        val isHoliday = typedArray.getBoolean(R.styleable.WeekCalendarItem_isHoliday, false)
        val dayOfWeek = typedArray.getString(R.styleable.WeekCalendarItem_dayOfWeek)
        val date = typedArray.getString(R.styleable.WeekCalendarItem_date)

        binding.txtDate.text = date
        binding.txtDayOfWeek.text = dayOfWeek

        val purpleColor = ContextCompat.getColor(context, R.color.purple)
        val redColor = ContextCompat.getColor(context, R.color.red)
        val blueColor = ContextCompat.getColor(context, R.color.blue)

        if (isSelect) { binding.cardView.setCardBackgroundColor(purpleColor) }
        if (isToday) { binding.cardView.strokeColor = purpleColor }

        when {
            isHoliday || dayOfWeek == "일" -> {
                binding.txtDate.setTextColor(redColor)
                binding.txtDayOfWeek.setTextColor(redColor)
            }
            dayOfWeek == "토" -> {
                binding.txtDate.setTextColor(blueColor)
                binding.txtDayOfWeek.setTextColor(blueColor)
            }
        }

    }

}