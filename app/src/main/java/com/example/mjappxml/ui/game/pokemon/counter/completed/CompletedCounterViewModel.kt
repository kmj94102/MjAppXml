package com.example.mjappxml.ui.game.pokemon.counter.completed

import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonCounter
import com.example.communication.repository.PokemonRepository
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
class CompletedCounterViewModel @Inject constructor(
    private val repository: PokemonRepository
): BaseViewModel() {

    private val _list = MutableStateFlow(listOf<PokemonCounter>())
    val list: StateFlow<List<PokemonCounter>> = _list

    init {
        fetchCompletedCounter()
    }

    private fun fetchCompletedCounter() {
        repository
            .fetchCompletedPokemonCounter()
            .onEach { _list.value = it }
            .catch { updateMessage(it.message ?: "조회 중 오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateDelete(index: Int) = viewModelScope.launch {
        repository
            .deletePokemonCounter(index)
    }

    fun updateRestore(number: String, index: Int) = viewModelScope.launch {
        repository
            .updateRestore(number, index)
    }

}