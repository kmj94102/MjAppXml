package com.example.mjappxml.ui.accountbook.add.fixed

import androidx.lifecycle.viewModelScope
import com.example.communication.repository.AccountBookRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.model.FixedAccountBookInfo
import com.example.mjappxml.model.mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FixedAccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository
) : BaseViewModel() {

    private val _list = MutableStateFlow(listOf<FixedAccountBookInfo>())
    val list: StateFlow<List<FixedAccountBookInfo>> = _list

    init {
        fetchFixedAccountBook()
    }

    fun fetchFixedAccountBook() {
        repository
            .fetchFixedAccountBook()
            .setLoadingState()
            .onEach { _list.value = it.mapper() }
            .catch { updateMessage(it.message ?: "조회 중 오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateSelectItem(id: Int) {
        _list.update { list ->
            list.map { it.copy(isSelect = it.id == id) }
        }
    }

    fun getOnSelectItem() =
        _list.value.firstOrNull { it.isSelect }

}