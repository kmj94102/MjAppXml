package com.example.mjappxml.ui.game.pokemon.dex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonSummary
import com.example.communication.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    private var _isShiny = MutableStateFlow(false)
    val isShiny: StateFlow<Boolean> = _isShiny

    private val _pokemonList = MutableStateFlow(listOf<PokemonSummary>())
    val pokemonList: StateFlow<List<PokemonSummary>> = _pokemonList

    private var isMore = true
    private var skip = 0
    private val limit = 100

    init {
        fetchPokemonList()
    }

    fun toggleIsShiny() {
        _isShiny.value = _isShiny.value.not()
    }

    fun fetchPokemonList() {
        if (isMore.not()) return

        repository
            .fetchPokemonList(
                name = "",
                skip = skip,
                limit = limit
            )
            .onStart {  }
            .onEach { (isMore, list) ->
                this.isMore = isMore
                skip += 1
                _pokemonList.value = _pokemonList.value + list
            }
            .onCompletion {  }
            .launchIn(viewModelScope)
    }

}