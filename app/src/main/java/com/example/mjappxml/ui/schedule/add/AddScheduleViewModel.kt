package com.example.mjappxml.ui.schedule.add

import androidx.lifecycle.viewModelScope
import com.example.communication.model.ScheduleModifier
import com.example.communication.repository.CalendarRepository
import com.example.mjappxml.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    private val repository: CalendarRepository,

) : BaseViewModel() {

    private val _item = MutableStateFlow(ScheduleModifier())
    val item: StateFlow<ScheduleModifier> = _item

    fun updateDate(date: String) {
        _item.value = _item.value.copy(date = date, recurrenceEndDate = date)
    }

    fun updateRepeatEndDateSelect(date: String) {
        _item.value = _item.value.copy(recurrenceEndDate = date)
    }

    fun updateStartTime(startTime: String) {
        _item.value = _item.value.copy(startTime = startTime, endTime = startTime)
    }

    fun updateEndTime(endTime: String) {
        _item.value = _item.value.copy(endTime = endTime)
    }

    fun updateRecurrenceType(recurrenceType: String) {
        _item.value = _item.value.copy(recurrenceType = recurrenceType)
    }

    fun insertSchedule() = viewModelScope.launch {
        repository
            .insertSchedule(_item.value)
            .onSuccess {
                updateMessage(it)
                updateFinish(true)
            }
            .onFailure {
                updateMessage(it.message ?: "등록 실패")
            }
    }

}