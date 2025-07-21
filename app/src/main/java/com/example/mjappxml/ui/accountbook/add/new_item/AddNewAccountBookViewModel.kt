package com.example.mjappxml.ui.accountbook.add.new_item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.communication.model.AccountBookInsertItem
import com.example.communication.repository.AccountBookRepository
import com.example.communication.util.removeNumberFormat
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.Constants
import com.example.mjappxml.ui.accountbook.add.WhereToUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _item = MutableStateFlow(AccountBookInsertItem.initItem())
    val item: StateFlow<AccountBookInsertItem> = _item

    private val _whereToUseList = MutableStateFlow(WhereToUse.getWhereToUseList())
    val whereToUseList: StateFlow<List<WhereToUse>> = _whereToUseList

    init {
        savedStateHandle.get<String>(Constants.Date)?.let {
            _item.value = _item.value.copy(date = it)
        }
    }

    fun insertNewAccountBookItem(amountValue: String) = viewModelScope.launch {
        val selectItem = _whereToUseList.value.first { it.isSelect }
        val amount = runCatching {
            amountValue.removeNumberFormat().toInt()
        }.getOrNull() ?: run {
            updateMessage("금액을 입력해주세요.")
            return@launch
        }

        _item.value = _item.value.copy(
            usageType = selectItem.usageType,
            amount = amount
        )

        val check = _item.value.checkValidity()
        if (check.isNotEmpty()) {
            updateMessage(check)
            return@launch
        }

        repository
            .insertNewAccountBookItem(_item.value)
            .onSuccess { updateFinish(true) }
            .onFailure { updateMessage(it.message ?: "등록에 실패하였습니다.") }
    }

    fun updateDate(value: String) {
        _item.update { it.copy(date = value) }
    }

    fun updateIsIncome(value: Boolean) {
        _item.update { it.copy(isIncome = value) }
    }

    fun updateIsAddFrequently(value: Boolean) {
        _item.update { it.copy(isAddFrequently = value) }
    }

    fun updateWhereToUse(item: WhereToUse) {
        _whereToUseList.value = _whereToUseList.value.map {
            it.copy(isSelect = it.name == item.name)
        }
    }

}