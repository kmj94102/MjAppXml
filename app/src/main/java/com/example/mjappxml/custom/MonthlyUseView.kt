package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.communication.model.ThisYearSummaryItem
import com.example.mjappxml.R
import com.example.mjappxml.common.getCardColor
import com.example.mjappxml.databinding.CustomMonthlyUseBinding

class MonthlyUseView: ConstraintLayout {

    private val binding by lazy {
        CustomMonthlyUseBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_monthly_use, this, false)
        )
    }

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int)
            : super(context, attributeSet, defStyle) {
        initViews()
    }

    fun initViews() {
        addView(binding.root)
    }

    fun setItem(item: ThisYearSummaryItem) {
        binding.item = item
        binding.cardView.setTopCardColor(item.getCardColor())
    }

}