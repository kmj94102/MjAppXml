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

    private var isShiny = false

    private val _pokemonList = MutableStateFlow(listOf<PokemonSummary>())
    val pokemonList: StateFlow<List<PokemonSummary>> = _pokemonList

    init {
        fetchPokemonList()
    }

    fun toggleIsShiny(): Boolean {
        isShiny = !isShiny
        return isShiny
    }

    fun fetchPokemonList() {
        repository
            .fetchPokemonList(
                name = "",
                skip = 0,
                limit = 100
            )
            .onStart {  }
            .onEach { (isMore, list) ->
                _pokemonList.value = _pokemonList.value + list
            }
            .onCompletion {  }
            .launchIn(viewModelScope)
    }

}