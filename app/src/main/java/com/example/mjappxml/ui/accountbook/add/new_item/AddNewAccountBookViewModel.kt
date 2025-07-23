package com.example.mjappxml.ui.accountbook.add.new_item

import androidx.lifecycle.viewModelScope
import com.example.communication.model.AccountBookInsertItem
import com.example.communication.repository.AccountBookRepository
import com.example.communication.util.removeNumberFormat
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.getToday
import com.example.mjappxml.model.FixedAccountBookInfo
import com.example.mjappxml.ui.accountbook.add.WhereToUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class AddNewAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val _item = MutableStateFlow(AccountBookInsertItem.initItem(getToday()))
    val item: StateFlow<AccountBookInsertItem> = _item

    private val _whereToUseList = MutableStateFlow(WhereToUse.getWhereToUseList())
    val whereToUseList: StateFlow<List<WhereToUse>> = _whereToUseList

    fun insertNewAccountBookItem(amountValue: String) = viewModelScope.launch {
        val amount = runCatching {
            amountValue.removeNumberFormat().toInt()
        }.getOrNull() ?: run {
            updateMessage("금액을 입력해주세요.")
            return@launch
        }

        _item.value = _item.value.copy(amount = amount)

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
        _item.update { it.copy(usageType = item.usageType) }
    }

    fun updateFromFixedItem(item: FixedAccountBookInfo) {
        _item.update {
            it.copy(
                isIncome = item.isIncome,
                amount = abs(item.amount),
                usageType = item.usageType,
                whereToUse = item.whereToUse
            )
        }
        _whereToUseList.value = _whereToUseList.value.map {
            it.copy(isSelect = it.usageType == item.usageType)
        }
    }

}