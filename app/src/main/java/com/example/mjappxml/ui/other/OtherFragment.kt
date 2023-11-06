package com.example.mjappxml.ui.other

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.mjappxml.BaseViewFragment
import com.example.mjappxml.R
import com.example.mjappxml.custom.CustomBottomNavigationItem
import com.example.mjappxml.databinding.FragmentOtherBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class OtherFragment : BaseViewFragment<FragmentOtherBinding>(R.layout.fragment_other) {

    val home = MutableStateFlow(
        CustomBottomNavigationItem.NavigationItem.init()
    )
    val game = MutableStateFlow(
        CustomBottomNavigationItem.NavigationItem.init()
    )
    val calendar = MutableStateFlow(
        CustomBottomNavigationItem.NavigationItem.init()
    )
    val accountBook = MutableStateFlow(
        CustomBottomNavigationItem.NavigationItem.init()
    )
    val animator1 = MutableStateFlow(2f)
    val animator2 = MutableStateFlow(2f)
    val animator3 = MutableStateFlow(2f)
    val animator4 = MutableStateFlow(2f)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        initViews()

        binding.view1.setOnClickListener {
            home.update { it.apply { isCurrentItem = true } }
            animator1.value = 5f
        }

        binding.view2.setOnClickListener {
            game.update { it.apply { isCurrentItem = true } }
            animator2.value = 5f
        }

        binding.view3.setOnClickListener {
            calendar.update { it.apply { isCurrentItem = true } }
            animator3.value = 5f
        }

        binding.view4.setOnClickListener {
            accountBook.update { it.apply { isCurrentItem = true } }
            animator4.value = 5f
        }

        binding.button.setOnClickListener {
            home.update { it.apply { isCurrentItem = isCurrentItem.not() } }
        }

    }

    private fun initViews() {
        home.value = CustomBottomNavigationItem.NavigationItem(
            title = resources.getString(R.string.home),
            imageRes = R.drawable.ic_home,
            isCurrentItem = true
        )
        game.value = CustomBottomNavigationItem.NavigationItem(
            title = resources.getString(R.string.game),
            imageRes = R.drawable.ic_game_pad,
            isCurrentItem = false
        )
        calendar.value = CustomBottomNavigationItem.NavigationItem(
            title = resources.getString(R.string.calendar),
            imageRes = R.drawable.ic_calendar,
            isCurrentItem = false
        )
        accountBook.value = CustomBottomNavigationItem.NavigationItem(
            title = resources.getString(R.string.account_book),
            imageRes = R.drawable.ic_flower,
            isCurrentItem = false
        )
    }

}