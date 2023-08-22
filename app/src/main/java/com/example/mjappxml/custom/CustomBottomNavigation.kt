package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.mjappxml.MainActivity
import com.example.mjappxml.databinding.CustomBottomNavigationBinding

class CustomBottomNavigation : LinearLayout {

    private val binding = CustomBottomNavigationBinding.inflate(LayoutInflater.from(context))
    private var activity: MainActivity? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    )

    init {
        binding.custom = this@CustomBottomNavigation
        addView(binding.root)
        binding.root.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun setActivity(activity: MainActivity) { this.activity = activity }

    fun goToHome(view: View) { activity?.run { goToHome() } }

    fun goToGame(view: View) { activity?.run { goToGame() } }

    fun goToSchedule(view: View) { activity?.run { goToSchedule() } }

    fun goToAccountBook(view: View) { activity?.run { goToAccountBook() } }

    fun goToOther(view: View) { activity?.run { goToOther() } }

}