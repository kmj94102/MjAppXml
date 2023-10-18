package com.example.mjappxml.ui.game.elsword.counter

import androidx.lifecycle.viewModelScope
import com.example.communication.model.ElswordQuest
import com.example.communication.model.ElswordQuestSimple
import com.example.communication.model.printStackTrace
import com.example.communication.repository.ElswordRepository
import com.example.mjappxml.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElswordCounterAddViewModel @Inject constructor(
    private val repository: ElswordRepository
) : BaseViewModel() {

    private val _list = MutableStateFlow<List<ElswordQuestSimple>>(listOf())
    val list: StateFlow<List<ElswordQuestSimple>> = _list

    init {
        fetchQuestList()
    }

    private fun fetchQuestList() {
        repository
            .fetchQuestList()
            .setLoadingState()
            .onEach { _list.value = it }
            .catch {
                _list.value = listOf()
                updateMessage("조회를 실패하였습니다.")
            }
            .launchIn(viewModelScope)
    }

    fun addCounter(
        name: String,
        max: Int,
        onSuccess: () -> Unit
    ) = viewModelScope.launch {
        repository
            .insertQuest(ElswordQuest(name = name, max = max))
            .onSuccess {
                updateMessage(it)
                fetchQuestList()
                onSuccess()
            }
            .onFailure {
                updateMessage("등록에 실패하였습니다.")
                it.printStackTrace()
            }
    }

    fun deleteCounter(id: Int) = viewModelScope.launch {
        repository
            .deleteQuest(id)
            .onSuccess {
                updateMessage("삭제 완료하였습니다.")
                fetchQuestList()
            }
            .onFailure { updateMessage("삭제 실패하였습니다.") }
            .printStackTrace()
    }
}