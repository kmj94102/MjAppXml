package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.mjappxml.databinding.CustomCalendarItemBinding
import com.example.mjappxml.R
import com.example.mjappxml.common.dpToPx

class CustomCalendarItem<T> : LinearLayout {

    private val binding = CustomCalendarItemBinding.inflate(LayoutInflater.from(context))
    private var onClickListener: ((T) -> Unit)? = null
    private var item: T? = null
    private var isToday = false
    private var primaryColor = R.color.purple

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

        binding.cardView.apply {
            strokeColor =
                ContextCompat.getColor(context, if (isToday) primaryColor else R.color.white)
//            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            layoutParams = LayoutParams(context.dpToPx(35), context.dpToPx(35))
            setOnClickListener {
                item?.let { onClickListener?.invoke(it) }
            }
        }
    }

    fun setDay(day: String) {
        binding.day = day
    }

    fun setItem(item: T) {
        this.item = item
    }

    fun setOnclickListener(listener: (T) -> Unit) {
        onClickListener = listener
    }

    fun setPrimaryColor(@ColorRes colorRes: Int) {
        primaryColor = colorRes
    }

    fun setDateInfo(dateInfo: DateInfo) {
        val color = when (dateInfo) {
            DateInfo.IsHoliday -> {
                R.color.red
            }

            DateInfo.IsNormal -> {
                R.color.black
            }

            DateInfo.IsSaturday -> {
                R.color.blue
            }
        }

        binding.txtDay.setTextColor(ContextCompat.getColor(context, color))
    }

    fun updateSelect(isSelect: Boolean) {
        val color = if (isSelect) primaryColor else R.color.white
        binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context, color))
    }

    sealed class DateInfo {
        object IsHoliday : DateInfo()
        object IsSaturday : DateInfo()
        object IsNormal : DateInfo()
    }
}