package com.example.mjappxml.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import com.example.communication.model.MyCalendar
import com.example.mjappxml.databinding.CustomCalendarBinding
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.common.getToday

class CustomCalendar : LinearLayout {

    private val binding = CustomCalendarBinding.inflate(LayoutInflater.from(context))
    private var primaryColor = ContextCompat.getColor(context, R.color.purple)
    private var selectDate = getToday()
    private var selectItem: CustomCalendarItem? = null
    private var onChangeListener: (String) -> Unit = {}

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
        setDayTitle()
    }

    private fun getAttrs(attributeSet: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomCalendar)
        attributeSetting(typeArray)
    }

    private fun attributeSetting(typedArray: TypedArray) {
        primaryColor = typedArray.getColor(
            R.styleable.CustomCalendar_colorPrimary,
            ContextCompat.getColor(context, R.color.purple)
        )

        binding.cardView.setBottomCardColor(primaryColor)
        typedArray.recycle()
    }

    private fun setDayTitle() {
        val dayList = listOf("일", "월", "화", "수", "목", "금", "토")
        dayList.forEachIndexed { index, day ->
            val color = when (day) {
                "일" -> R.color.red
                "토" -> R.color.blue
                else -> R.color.black
            }
            val layoutParams = GridLayout.LayoutParams()
            layoutParams.columnSpec = GridLayout.spec(index % 7, 1, 1f)
            layoutParams.bottomMargin = context.dpToPx(5)

            val textView = TextView(context).also { view ->
                view.setTextSize(Dimension.SP, 16f)
                view.setTypeface(null, Typeface.BOLD)
                view.setTextColor(ContextCompat.getColor(context, color))
                view.gravity = Gravity.CENTER
                view.text = day
                view.layoutParams = layoutParams
            }
            binding.gridLayout.addView(textView)
        }
    }

    fun setList(list: List<MyCalendar>) {
        binding.gridLayout.removeAllViews()
        setDayTitle()

        list.forEachIndexed { index, item ->
            val layoutParams = GridLayout.LayoutParams()
            layoutParams.columnSpec = GridLayout.spec(index % 7, 1, 1f)
            layoutParams.bottomMargin = context.dpToPx(5)

            val calendarItem = CustomCalendarItem(context).also { view ->
                view.setItem(item)
                view.setPrimaryColor(primaryColor)
                view.setOnclickListener {
                    view.updateSelect(true)
                    if (selectItem != view) {
                        selectItem?.updateSelect(false)
                    }
                    selectItem = view
                    onChangeListener(it)
                }
                if (item.detailDate == selectDate) {
                    view.updateSelect(true)
                    selectItem = view
                    onChangeListener(item.detailDate)
                }
                view.layoutParams = layoutParams
            }

            binding.gridLayout.addView(calendarItem)
        }
    }

    fun setOnChangeListener(changeListener: (String) -> Unit) {
        onChangeListener = changeListener
    }

    fun updateSelectDate(date: String) {
        selectDate = date
    }

}