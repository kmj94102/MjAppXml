package com.example.mjappxml.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.communication.model.CalendarResult
import com.example.communication.model.HomeInfoResult
import com.example.communication.model.HomeParam
import com.example.communication.model.getWeeklyDateRange
import com.example.communication.repository.HomeRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.getLastDayOfWeek
import com.example.mjappxml.common.getStartDayOfWeek
import com.example.mjappxml.common.getToday
import com.example.mjappxml.common.toStringFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    val today = getToday()
    val yearMonth
        get() = getToday("yyyy년 MM월")

    private val initList = getWeeklyDateRange(today)
    private val sharedFlow = MutableSharedFlow<Pair<String, String>>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val homeInfo: StateFlow<HomeInfoResult> = sharedFlow.flatMapLatest { (start, end) ->
        repository
            .fetchHomeInfo(HomeParam(start, end))
            .catch { it.printStackTrace() }
            .map {
                it.copy(
                    calendarInfo = setCalendarItem(it.calendarInfo)
                )
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = HomeInfoResult().copy(
            calendarInfo = initList
        )
    )

    private val _selectDate = MutableStateFlow(today)
    val selectDate: StateFlow<String> = _selectDate

    init {
        fetchHomeInfo()
    }

    private fun fetchHomeInfo() {
        val start = getStartDayOfWeek(today)?.toStringFormat() ?: return
        val end = getLastDayOfWeek(today)?.toStringFormat() ?: return

        sharedFlow.tryEmit(start to end)
    }

    private fun setCalendarItem(list: List<CalendarResult>) = initList.map {
        Log.e("+++++", "selectDate: ${selectDate.value}\ndate: ${it.date}")
        list.firstOrNull { item -> it.date == item.date } ?: it
    }


}