package com.example.mjappxml.ui.accountbook

import androidx.lifecycle.viewModelScope
import com.example.communication.model.AccountBookMainInfo
import com.example.communication.model.DateConfiguration
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
class AccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val today = getToday()

    private val _info = MutableStateFlow(AccountBookMainInfo.create())
    val info: StateFlow<AccountBookMainInfo> = _info

    private val _lastMonthUsedList = MutableStateFlow(listOf<LastMonthUsed>())
    val lastMonthUsedList: StateFlow<List<LastMonthUsed>> = _lastMonthUsedList

    init {
        fetchAccountBookInfo()
    }

    private fun fetchAccountBookInfo() {
        repository
            .fetchAccountBookInfo(
                DateConfiguration(
                    baseDate = 25,
                    date = "${today.replace(".", "-")}T10:00:00.000Z"
                )
            )
            .setLoadingState()
            .onEach {
                _info.value = it
                _lastMonthUsedList.value = it.lastMonthAnalysis.result.map { item -> item.mapper() }
            }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

}