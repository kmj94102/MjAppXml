package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.RadioButton
import androidx.annotation.IdRes

class RadioGridGroup : GridLayout, View.OnClickListener {

    @IdRes
    var selectRadiobuttonId: Int?= null
        get() = getSelectRadioButton()?.id
        private set

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int)
            : super(context, attributeSet, defStyle)

    private fun getSelectRadioButton(): RadioButton? {
        for (index in 0 until childCount) {
            val radioButton = getChildAt(index) as? RadioButton
            if (radioButton?.isChecked == true) return radioButton
        }
        return null
    }

    override fun onClick(view: View) {
        for (index in 0 until childCount) {
            val radioButton = getChildAt(index) as? RadioButton
            radioButton?.isChecked = false
        }
        val radioButton = view as? RadioButton
        radioButton?.isChecked = true
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        child?.setOnClickListener(this)
    }

    fun initSelect(type: String) {
        for (index in 0 until childCount) {
            val radioButton = getChildAt(index) as? RadioButton
            if (radioButton?.tag == type) {
                radioButton.isChecked = true
            }
        }
    }

}