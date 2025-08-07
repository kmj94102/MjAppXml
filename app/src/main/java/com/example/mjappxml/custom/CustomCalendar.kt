package com.example.mjappxml.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.example.communication.model.MyCalendar
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx
import com.example.mjappxml.common.getToday
import com.example.mjappxml.databinding.CustomCalendarBinding

class CustomCalendar : LinearLayout {

    private val binding = CustomCalendarBinding.inflate(LayoutInflater.from(context))
    private var primaryColor = ContextCompat.getColor(context, R.color.purple)
    private var selectDate = getToday()
    private var selectItem: CustomCalendarItem? = null
    private var onChangeListener: (String) -> Unit = {}
    private var onPrevMonthListener: () -> Unit = {}
    private var onNextMonthListener: () -> Unit = {}

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
        binding.btnPrev.setOnClickListener { onPrevMonthListener.invoke() }
        binding.btnNext.setOnClickListener { onNextMonthListener.invoke() }
        binding.cardView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        setDayTitle()
    }

    private fun setDayTitle() {
        val dayList = listOf("일", "월", "화", "수", "목", "금", "토")
        dayList.forEachIndexed { index, day ->
            val layoutParams = GridLayout.LayoutParams()
            layoutParams.columnSpec = GridLayout.spec(index % 7, 1, 1f)
            layoutParams.bottomMargin = context.dpToPx(5)

            val textView = TextView(context).also { view ->
                view.setTextSize(Dimension.SP, 12f)
                view.setTypeface(null, Typeface.NORMAL)
                view.setTextColor("#FF9EA3B2".toColorInt())
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

        runCatching {
            list.first { it.detailDate.isNotEmpty() }.apply {
                binding.txtDate.text =
                    this.convertFormat(this.detailDate, "yyyy.MM.dd", "yyyy년 MM월")
            }
        }

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

    fun setOnPrevMonthClickListener(onClick: () -> Unit) {
        onPrevMonthListener = onClick
    }

    fun setOnNextMonthClickListener(onClick: () -> Unit) {
        onNextMonthListener = onClick
    }

}