package com.example.mjappxml.ui.schedule

import androidx.lifecycle.viewModelScope
import com.example.communication.model.MyCalendar
import com.example.communication.model.MyCalendarInfo
import com.example.communication.model.fetchMyCalendarByMonth
import com.example.communication.repository.CalendarRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.customCatch
import com.example.mjappxml.common.getNextMonth
import com.example.mjappxml.common.getPrevMonth
import com.example.mjappxml.common.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: CalendarRepository
) : BaseViewModel() {

    private val today = getToday()
    private val _selectDate = MutableStateFlow(today)
    val selectDate: StateFlow<String> = _selectDate

    private val year get() = run { _selectDate.value.substring(0, 4).toInt() }
    private val month get() = run { _selectDate.value.substring(5, 7).toInt() }

    private val _list = MutableStateFlow<List<MyCalendar>>(listOf())
    val list: StateFlow<List<MyCalendar>> = _list

    private val _selectItem = MutableStateFlow(MyCalendar())
    val selectItem: StateFlow<MyCalendar> = _selectItem

    init {
        fetchCalendar()
    }

    private fun fetchCalendar() {
        val list = fetchMyCalendarByMonth(year = year, month = month)
        _list.value = list
        updateSelectItem()
        fetchCalendarByMonth()
    }

    private fun fetchCalendarByMonth() {
        repository
            .fetchCalendarByMonth(year = year, month = month)
            .onStart { startLoading() }
            .onEach {
                it.forEach { info -> setCalendarInfo(info) }
            }
            .onCompletion { endLoading() }
            .customCatch(
                onNetworkError = { updateNetworkErrorState(true) },
                onError = { updateMessage(it ?: "조회 중 오류가 발생하였습니다.") }
            )
            .launchIn(viewModelScope)
    }

    private fun setCalendarInfo(info: MyCalendarInfo) {
        val index = _list.value.indexOfFirst { myCalendar ->
            myCalendar.detailDate == info.date
        }
        if (index != -1) {
            val newList = _list.value.toMutableList()
            newList[index] = newList[index].copy(
                isHoliday = info.isHoliday,
                isSpecialDay = info.isSpecialDay,
                dateInfo = info.info,
                itemList = info.list.toMutableList()
            )
            _list.value = newList
        }
    }

    fun updateSelectDate(selectDate: String) {
        _selectDate.value = selectDate
        updateSelectItem()
    }

    private fun updateSelectItem() {
        _selectItem.value = _list.value.find { it.detailDate == _selectDate.value } ?: MyCalendar()
    }

    fun updateYearMonth(year: String, month: String) {
        _selectDate.value = "$year.$month.01"
        fetchCalendar()
    }

    fun updatePrevMonth() {
        _selectDate.value = getPrevMonth(_selectDate.value)
        fetchCalendar()
    }

    fun updateNextMonth() {
        _selectDate.value = getNextMonth(_selectDate.value)
        fetchCalendar()
    }

}