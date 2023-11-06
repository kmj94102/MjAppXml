package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CustomBottomNavgationItemBinding

class CustomBottomNavigationItem : LinearLayout {

    private val binding = CustomBottomNavgationItemBinding.inflate(LayoutInflater.from(context))

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
        binding.root.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun setItem(item: NavigationItem) {
        binding.item = item
        binding.invalidateAll()
    }

    data class NavigationItem(
        val title: String,
        @DrawableRes
        val imageRes: Int,
        var isCurrentItem: Boolean
    ) {
        fun getWeight() = if (isCurrentItem) 5f else 2f

        companion object {
            fun init() = NavigationItem(
                title = "",
                imageRes = R.drawable.ic_home,
                isCurrentItem = false
            )
        }
    }

}