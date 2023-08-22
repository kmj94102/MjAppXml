package com.example.mjappxml.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.example.mjappxml.R
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mjappxml.databinding.CustomBottomNavgationItemBinding

class CustomBottomNavigationItem : LinearLayout {

    private val binding = CustomBottomNavgationItemBinding.inflate(LayoutInflater.from(context))

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

    private fun getAttrs(attributeSet: AttributeSet){
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomBottomNavigationItem)
        attributeSetting(typeArray)
    }

    private fun attributeSetting(typedArray: TypedArray) {
        binding.title = typedArray.getString(R.styleable.CustomBottomNavigationItem_navigationTitle)
        binding.isCurrentItem =
            typedArray.getBoolean(R.styleable.CustomBottomNavigationItem_isCurrentItem, false)
        binding.imageRes = typedArray.getResourceId(
            R.styleable.CustomBottomNavigationItem_navigationIcon,
            R.drawable.ic_home
        )

        typedArray.recycle()
    }

    private fun initViews() {
        addView(binding.root)
        binding.root.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun setItem(
        title: String,
        @DrawableRes imageRes: Int,
        isCurrentItem: Boolean
    ) = with(binding) {
        this.title = title
        this.imageRes = imageRes
        this.isCurrentItem = isCurrentItem
    }

    fun updateCurrentItem(isCurrentItem: Boolean) = run {
        binding.isCurrentItem == isCurrentItem
    }

}