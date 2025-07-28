package com.example.mjappxml.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.communication.model.MyCalendarInfo
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CustomWeekCalendarItemBinding

class WeekCalendarItem : ConstraintLayout {

    private val binding = CustomWeekCalendarItemBinding.inflate(LayoutInflater.from(context))
    private val purpleColor = ContextCompat.getColor(context, R.color.purple)
    private val redColor = ContextCompat.getColor(context, R.color.red)
    private val blueColor = ContextCompat.getColor(context, R.color.blue)

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

        if (isSelect) {
            binding.cardView.setCardBackgroundColor(purpleColor)
        }
        if (isToday) {
            binding.cardView.strokeColor = purpleColor
        }

        setDateStatus(isHoliday, dayOfWeek ?: "")
    }

    private fun setDateStatus(isHoliday: Boolean, dayOfWeek: String) {
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

    fun setCalendarInfo(info: MyCalendarInfo, today: String) {
        binding.txtDate.text = info.date.takeLast(2)
        if (info.date == today) {
            binding.cardView.strokeColor = purpleColor
        }

        setDateStatus(
            isHoliday = info.isHoliday || info.isSpecialDay,
            dayOfWeek = binding.txtDayOfWeek.text.toString()
        )
    }

    fun setSelect(selectDate: String) {
        if (binding.txtDate.text.toString() == selectDate.takeLast(2)) {
            binding.cardView.setCardBackgroundColor(purpleColor)
        }
    }

}