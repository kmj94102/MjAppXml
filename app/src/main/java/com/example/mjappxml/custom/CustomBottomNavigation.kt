package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.mjappxml.databinding.CustomBottomNavigationBinding

class CustomBottomNavigation : LinearLayout {

    private val binding = CustomBottomNavigationBinding.inflate(LayoutInflater.from(context))
    private var beforeSelectId: Int? = null

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

    init {
        binding.custom = this@CustomBottomNavigation
        addView(binding.root)
        binding.root.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        beforeSelectId = binding.itemHome.id

        initViews()
    }

    private fun initViews() {

    }

    fun onItemClick(view: View) {
        if (beforeSelectId == view.id) return
        runCatching {
            beforeSelectId?.let {
                findViewById<CustomBottomNavigationItem>(it).updateCurrentItem(false)
            }
            (view as? CustomBottomNavigationItem)?.updateCurrentItem(true)
        }.onSuccess { beforeSelectId = view.id }.onFailure { it.printStackTrace() }
    }
}