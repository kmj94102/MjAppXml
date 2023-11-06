package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.example.mjappxml.MainActivity
import com.example.mjappxml.R
import com.example.mjappxml.binding.setLayoutWeightAnimated
import com.example.mjappxml.databinding.CustomBottomNavigationBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomBottomNavigation : LinearLayout {

    private val binding = CustomBottomNavigationBinding.inflate(LayoutInflater.from(context))
    private var activity: MainActivity? = null
    private val _itemList = MutableStateFlow(listOf<CustomBottomNavigationItem.NavigationItem>())
    val itemList: StateFlow<List<CustomBottomNavigationItem.NavigationItem>> = _itemList

    val homeWeight = MutableStateFlow(5f)
    val gameWeight = MutableStateFlow(2f)
    val calendarWeight = MutableStateFlow(2f)
    val accountBookWeight = MutableStateFlow(2f)
    val otherWeight = MutableStateFlow(2f)

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
        fetchNavigationItems()
    }

    private fun fetchNavigationItems() {
        _itemList.value = listOf(
            CustomBottomNavigationItem.NavigationItem(
                title = resources.getString(R.string.home),
                imageRes = R.drawable.ic_home,
                isCurrentItem = true
            ),
            CustomBottomNavigationItem.NavigationItem(
                title = resources.getString(R.string.game),
                imageRes = R.drawable.ic_game_pad,
                isCurrentItem = false
            ),
            CustomBottomNavigationItem.NavigationItem(
                title = resources.getString(R.string.calendar),
                imageRes = R.drawable.ic_calendar,
                isCurrentItem = false
            ),
            CustomBottomNavigationItem.NavigationItem(
                title = resources.getString(R.string.account_book),
                imageRes = R.drawable.ic_flower,
                isCurrentItem = false
            ),
            CustomBottomNavigationItem.NavigationItem(
                title = resources.getString(R.string.other),
                imageRes = R.drawable.ic_other,
                isCurrentItem = false
            ),
        )
    }


    fun setActivity(activity: MainActivity) {
        this.activity = activity
        activity.lifecycleScope.launch {
            _itemList.collectLatest {
                runCatching {
                    settingItem(binding.itemHome, it[0])
                    settingItem(binding.itemGame, it[1])
                    settingItem(binding.itemSchedule, it[2])
                    settingItem(binding.itemAccountBook, it[3])
                    settingItem(binding.itemOther, it[4])
                }
            }
        }
    }

    private fun settingItem(
        view: CustomBottomNavigationItem,
        item: CustomBottomNavigationItem.NavigationItem
    ) {
        view.setItem(item)
        setLayoutWeightAnimated(view, item.getWeight())
    }

    fun onItemClick(view: View) {
        _itemList.update {
            it.map { item ->
                val isCurrent = view.tag.toString() == item.title
                if (isCurrent && item.isCurrentItem) return
                item.copy(isCurrentItem = isCurrent)
            }
        }

        goToPage(view.id)
    }

    private fun goToPage(id: Int) {
        val mainActivity: MainActivity = activity ?: return
        when(id) {
            binding.itemHome.id -> {
                mainActivity.goToHome()
            }
            binding.itemGame.id -> {
                mainActivity.goToGame()
            }
            binding.itemSchedule.id -> {
                mainActivity.goToSchedule()
            }
            binding.itemAccountBook.id -> {
                mainActivity.goToAccountBook()
            }
            binding.itemOther.id -> {
                mainActivity.goToOther()
            }
        }
    }

}