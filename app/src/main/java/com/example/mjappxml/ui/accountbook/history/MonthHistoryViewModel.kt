package com.example.mjappxml.ui.accountbook.history

import androidx.lifecycle.viewModelScope
import com.example.communication.model.AccountBookDetailInfo
import com.example.communication.model.AccountBookItem
import com.example.communication.model.CalendarItem
import com.example.communication.model.DateConfiguration
import com.example.communication.model.MyCalendar
import com.example.communication.model.fetchMyCalendarByDate
import com.example.communication.model.fetchMyCalendarByMonth
import com.example.communication.repository.AccountBookRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MonthHistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val today = getToday()
    private val _selectDate = MutableStateFlow(today)
    val selectDate: StateFlow<String> = _selectDate

    private val year get() = run { _selectDate.value.substring(0, 4).toInt() }
    private val month get() = run { _selectDate.value.substring(5, 7).toInt() }

    private val _info = MutableStateFlow(AccountBookDetailInfo.init())
    val info: StateFlow<AccountBookDetailInfo> = _info

    private val _list = MutableStateFlow<List<MyCalendar>>(listOf())
    val list: StateFlow<List<MyCalendar>> = _list

    private val _selectItem = MutableStateFlow(MyCalendar())
    val selectItem: StateFlow<MyCalendar> = _selectItem

    init {
        val list = fetchMyCalendarByMonth(year = year, month = month)
        _list.value = list
        _selectItem.value = MyCalendar()
        updateSelectItem()
    }

    fun fetchThisMonthDetail() {
        repository
            .fetchThisMonthDetail(DateConfiguration.create(date = today, baseDate = 25))
            .setLoadingState()
            .onEach {
                _info.value = it
                _list.value = fetchMyCalendarByDate(
                    startDate = it.startDate,
                    endDate = it.endDate,
                    list = it.list.map { item -> item.mapper() }
                )
            }
            .catch { updateMessage(it.message ?: "조회 중 오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    private fun AccountBookItem.mapper() = CalendarItem.AccountHistoryInfo(
        id = id,
        date = date.replace("-", "."),
        dateOfWeek = dateOfWeek,
        amount = amount,
        usageType = usageType,
        whereToUse = whereToUse
    )

    fun updateSelectDate(selectDate: String) {
        _selectDate.value = selectDate
        updateSelectItem()
    }

    private fun updateSelectItem() {
        _selectItem.value = _list.value.find { it.detailDate == _selectDate.value } ?: MyCalendar()
    }

}