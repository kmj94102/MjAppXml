package com.example.mjappxml.ui.accountbook.add.fixed

import androidx.lifecycle.viewModelScope
import com.example.communication.model.FixedAccountBook
import com.example.communication.repository.AccountBookRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.ui.accountbook.add.WhereToUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFixedAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
): BaseViewModel() {

    private val _isIncome = MutableStateFlow(true)
    val isIncome: StateFlow<Boolean> = _isIncome

    private val _item = MutableStateFlow(FixedAccountBook.create())
    val item: StateFlow<FixedAccountBook> = _item

    private val _whereToUseList = MutableStateFlow(WhereToUse.getWhereToUseList())
    val whereToUseList: StateFlow<List<WhereToUse>> = _whereToUseList

    fun insertFixedAccountBookItem(amount: Int) = viewModelScope.launch {
        val selectItem = _whereToUseList.value.first { it.isSelect }
        _item.value = _item.value.copy(
            usageType = selectItem.usageType,
            amount = amount
        )

        repository
            .insertFixedAccountBookItem(_item.value)
            .onSuccess {
                updateMessage("등록 완료")
                updateFinish(true)
            }
            .onFailure { updateMessage(it.message ?: "등록에 실패하였습니다.") }
    }

    fun updateDate(value: String) {
        _item.value = _item.value.copy(date = value)
    }

    fun updateIsIncome(value: Boolean) {
        _isIncome.value = value
    }

    fun updateWhereToUse(item: WhereToUse) {
        _whereToUseList.value = _whereToUseList.value.map {
            it.copy(isSelect = it.name == item.name)
        }
    }
}