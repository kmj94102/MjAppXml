package com.example.mjappxml.ui.schedule.add

import androidx.lifecycle.viewModelScope
import com.example.communication.model.PlanTasksModify
import com.example.communication.model.ScheduleModifier
import com.example.communication.model.TaskItem
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

    private val _isSchedule = MutableStateFlow(true)
    val isSchedule: StateFlow<Boolean> = _isSchedule

    private val _item = MutableStateFlow(ScheduleModifier())
    val item: StateFlow<ScheduleModifier> = _item

    private val _planItem = MutableStateFlow(PlanTasksModify())
    val planItem: StateFlow<PlanTasksModify> = _planItem

    fun updateIsSchedule(isSchedule: Boolean) {
        _isSchedule.value = isSchedule
    }

    fun updateDate(date: String) {
        _item.value = _item.value.copy(date = date, recurrenceEndDate = date)
        _planItem.value = _planItem.value.copy(planDate = date)
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

    fun addTaskItem() {
        _planItem.value = _planItem.value.copy(
            taskList = _planItem.value.taskList.toMutableList().apply {
                add(TaskItem(contents = ""))
            }
        )
    }

    fun removeTaskItem(index: Int) {
        _planItem.value = _planItem.value.copy(
            taskList = _planItem.value.taskList.toMutableList().apply {
                removeAt(index)
                if (isEmpty()) {
                    add(TaskItem(contents = ""))
                }
            }
        )
    }

    fun insertItem() {
        if (_isSchedule.value) {
            insertSchedule()
        } else {
            insertPlanTasks()
        }
    }

    private fun insertSchedule() = viewModelScope.launch {
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

    private fun insertPlanTasks() = viewModelScope.launch {
        repository
            .insertPlan(_planItem.value)
            .onSuccess {
                updateMessage(it)
                updateFinish(true)
            }
            .onFailure {
                updateMessage(it.message ?: "등록 실패")
            }
    }

}