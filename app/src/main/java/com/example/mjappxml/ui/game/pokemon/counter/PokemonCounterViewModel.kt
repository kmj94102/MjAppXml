package com.example.mjappxml.ui.game.pokemon.counter

import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonCounter
import com.example.communication.model.UpdatePokemonCatch
import com.example.communication.repository.PokemonRepository
import com.example.mjappxml.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonCounterViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {
    private var flag = false

    val list = repository.fetchPokemonCounter()
        .map {
            if (flag || it.find { item -> item.isSelect } != null) {
                flag = true
            } else if (it.isNotEmpty()) {
                repository.updateCounterSelect(it.indexOf(it.first()))

                flag = true
            }
            it
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = listOf()
        )

    val selectItem = list
        .map { list -> list.firstOrNull { it.isSelect } ?: PokemonCounter.init() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            PokemonCounter.init()
        )


    fun updateSelect(index: Int) = viewModelScope.launch {
        repository.updateCounterSelect(index)
    }

    fun updateCounter(count: Int) = viewModelScope.launch {
        repository.updateCounter(selectItem.value.count + count, selectItem.value.number)
    }

    fun deleteCounter() = viewModelScope.launch {
        repository.deletePokemonCounter(selectItem.value.number)
        runCatching {
            repository.updateCounterSelect(list.value.first().index)
        }
    }

    fun updateCatch() = viewModelScope.launch {
        repository.updateCatch(selectItem.value.number).runCatching {
            repository.updatePokemonCatch(
                UpdatePokemonCatch(selectItem.value.number, true)
            )
        }
        runCatching {
            repository.updateCounterSelect(list.value.first().index)
        }
    }

}