package com.example.mjappxml.ui.schedule.add

import androidx.lifecycle.viewModelScope
import com.example.communication.model.PlanTasksModify
import com.example.communication.model.TaskItem
import com.example.communication.repository.CalendarRepository
import com.example.mjappxml.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlanViewModel @Inject constructor(
    private val repository: CalendarRepository
): BaseViewModel() {

    private val _item = MutableStateFlow(PlanTasksModify())
    val item: StateFlow<PlanTasksModify> = _item

    fun updateDate(date: String) {
        _item.value = _item.value.copy(planDate = date)
    }

    fun addTaskItem() {
        _item.value = _item.value.copy(
            taskList = _item.value.taskList.toMutableList().apply {
                add(TaskItem(contents = ""))
            }
        )
    }

    fun removeTaskItem(index: Int) {
        _item.value = _item.value.copy(
            taskList = _item.value.taskList.toMutableList().apply {
                removeAt(index)
                if (isEmpty()) {
                    add(TaskItem(contents = ""))
                }
            }
        )
    }

    fun insertPlanTasks() = viewModelScope.launch {
        repository
            .insertPlan(_item.value)
            .onSuccess {
                updateMessage(it)
                updateFinish(true)
            }
            .onFailure {
                updateMessage(it.message ?: "등록 실패")
            }
    }

}