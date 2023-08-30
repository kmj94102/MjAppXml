package com.example.mjappxml.ui.game.pokemon.counter

import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonCounter
import com.example.communication.model.UpdatePokemonCatch
import com.example.communication.repository.PokemonRepository
import com.example.mjappxml.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonCounterViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val sharedFlow = MutableSharedFlow<Unit>(replay = 1)
    val counterList = sharedFlow.flatMapLatest {
        repository.fetchPokemonCounter()
            .map { it + PokemonCounter.init() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = listOf()
    )

    init {
        sharedFlow.tryEmit(Unit)
    }

    fun updateCustomIncrease(
        number: String,
        increase: Int
    ) = viewModelScope.launch {
        repository.updateCustomIncrease(increase, number)
    }

    fun updateCounter(
        counter: Int,
        number: String
    ) = viewModelScope.launch {
        repository.updateCounter(counter, number)
    }

    fun deleteCounter(number: String) = viewModelScope.launch {
        repository.deletePokemonCounter(number)
    }

    fun updateCatch(number: String) = viewModelScope.launch {
        repository.updateCatch(number).runCatching {
            repository.updatePokemonCatch(
                UpdatePokemonCatch(number, true)
            )
        }
    }

}