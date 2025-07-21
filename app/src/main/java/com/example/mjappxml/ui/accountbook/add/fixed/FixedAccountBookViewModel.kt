package com.example.mjappxml.ui.accountbook.add.fixed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.communication.model.FixedAccountBook
import com.example.communication.repository.AccountBookRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.Constants
import com.example.mjappxml.common.getToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixedAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _item = MutableStateFlow(listOf<FixedAccountBook>())
    val item: StateFlow<List<FixedAccountBook>> = _item

    private val _yearMonth = MutableStateFlow(getToday("yyyy.MM"))
    val yearMonth: StateFlow<String> = _yearMonth

    init {
        savedStateHandle.get<String>(Constants.Date)?.let {
            _yearMonth.value = it
        }
    }

    fun fetchFixedAccountBook() {
        repository
            .fetchFixedAccountBook()
            .setLoadingState()
            .onEach { _item.value = it }
            .catch { updateMessage(it.message ?: "조회 중 오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateYearMonth(year: String, month: String) {
        _yearMonth.value = "${year}.$month"
    }

    fun updateDate(date: String, selectIndex: Int) {
        if (selectIndex == -1 || selectIndex >= _item.value.size) return
        _item.update { list ->
            list.toMutableList().apply {
                this[selectIndex] = list[selectIndex].copy(date = date)
            }
        }
    }

    fun deleteItem(id: Int) = viewModelScope.launch {
        repository
            .deleteFixedAccountBookItem(id)
            .onSuccess {
                _item.update { list ->
                    list.toMutableList().apply {
                        removeAll { it.id == id }
                    }
                }
            }
            .onFailure { updateMessage(it.message ?: "삭제 실패하였습니다.") }

    }

    fun insertNewAccountBookItem(item: FixedAccountBook) = viewModelScope.launch {
        repository
            .insertNewAccountBookItem(
                item.toAccountBookInsertItem(_yearMonth.value),
            )
            .onSuccess {
                updateMessage("등록 완료")
                updateFinish()
            }
            .onFailure { updateMessage("등록에 실패하였습니다.") }
    }

}