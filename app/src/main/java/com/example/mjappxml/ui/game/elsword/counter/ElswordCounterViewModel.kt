package com.example.mjappxml.ui.game.elsword.counter

import androidx.lifecycle.viewModelScope
import com.example.communication.model.ElswordQuestDetail
import com.example.communication.repository.ElswordRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.customCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ElswordCounterViewModel @Inject constructor(
    private val repository: ElswordRepository
) : BaseViewModel() {

    private val _list = MutableStateFlow<List<ElswordQuestDetail>>(listOf())
    val list: StateFlow<List<ElswordQuestDetail>> = _list

    private var index = 0

    private val _selectItem = MutableStateFlow(ElswordQuestDetail.init())
    val selectItem: StateFlow<ElswordQuestDetail> = _selectItem

    init {
        fetchQuestDetailList()
    }

    fun fetchQuestDetailList() {
        repository
            .fetchQuestDetailList()
            .onStart { startLoading() }
            .onEach {
                _list.value = it
                setSelectItem()
                updateNetworkErrorState(false)
            }
            .customCatch(
                onNetworkError = { updateNetworkErrorState(true) },
                onError = { updateMessage(it ?: "조회 중 오류가 발생하였습니다.") }
            )
            .onCompletion { endLoading() }
            .launchIn(viewModelScope)
    }

    private fun setSelectItem() = runCatching {
        _selectItem.value = _list.value[index]
    }.onFailure {
        _selectItem.value = ElswordQuestDetail.init()
    }

    fun updateSelectItem(index: Int) {
        this.index = index
        setSelectItem()
    }

}