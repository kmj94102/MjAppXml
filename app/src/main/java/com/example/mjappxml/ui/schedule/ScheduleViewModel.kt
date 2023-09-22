package com.example.mjappxml.ui.schedule

import androidx.lifecycle.viewModelScope
import com.example.communication.model.MyCalendar
import com.example.communication.model.MyCalendarInfo
import com.example.communication.model.checkNetworkError
import com.example.communication.model.fetchMyCalendarByMonth
import com.example.communication.repository.CalendarRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.getToday
import com.example.mjappxml.model.toCustomCalendarInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: CalendarRepository
) : BaseViewModel() {

    val today = getToday()
    private val _selectDate = MutableStateFlow(today)
    val selectDate: StateFlow<String> = _selectDate

    val year get() = run { _selectDate.value.substring(0, 4) }
    val month get() = run { _selectDate.value.substring(5, 7) }

    private val _list = MutableStateFlow<List<MyCalendar>>(listOf())
    val list: StateFlow<List<MyCalendar>> = _list

    private val _selectItem = MutableStateFlow(MyCalendar())
    val selectItem: StateFlow<MyCalendar> = _selectItem

    private val _isCalendar = MutableStateFlow(true)
    val isCalendar: StateFlow<Boolean> = _isCalendar

    init {
        fetchCalendar()
    }

    fun fetchCalendar() {
        val list = fetchMyCalendarByMonth(year = year.toInt(), month = month.toInt())
        _list.value = list
        updateSelectItem()
        fetchCalendarByMonth()
    }

    fun fetchCalendarByMonth() {
        repository
            .fetchCalendarByMonth(year = year.toInt(), month = month.toInt())
            .onStart { startLoading() }
            .onEach {
                it.forEach { info -> setCalendarInfo(info) }
            }
            .onCompletion { endLoading() }
            .catch {
                if (it.checkNetworkError()) updateNetworkErrorState(true)
                else updateMessage(it.message ?: "불러오는 중 문제가 발생하였습니다.")
            }
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

    fun updateIsCalendar(isCalendar: Boolean) {
        _isCalendar.value = isCalendar
    }

}