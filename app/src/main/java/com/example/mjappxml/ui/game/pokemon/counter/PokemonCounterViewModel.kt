package com.example.mjappxml.ui.game.pokemon.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonCounter
import com.example.communication.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonCounterViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

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

}