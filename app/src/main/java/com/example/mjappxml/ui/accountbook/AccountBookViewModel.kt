package com.example.mjappxml.ui.accountbook

import androidx.lifecycle.viewModelScope
import com.example.communication.model.AccountBookDetailInfo
import com.example.communication.model.DateConfiguration
import com.example.communication.repository.AccountBookRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.getToday
import com.example.mjappxml.model.AccountBookInfo
import com.example.mjappxml.model.AccountBookInfo.Companion.convert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountBookViewModel @Inject constructor(
    repository: AccountBookRepository
) : BaseViewModel() {

    private val today = getToday()

    val info = repository
        .fetchThisMonthDetail(
            DateConfiguration(
                baseDate = 25,
                date = "${today.replace(".", "-")}T10:00:00.000Z"
            )
        )
        .setLoadingState()
        .onEach { _list.value = it.list.convert() }
        .catch { it.printStackTrace() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = AccountBookDetailInfo.init()
        )

    private val _list = MutableStateFlow<List<AccountBookInfo>>(emptyList())
    val list: StateFlow<List<AccountBookInfo>> = _list
}