package com.example.mjappxml.ui.game.pokemon.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communication.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel(){

    private val _isShiny = MutableStateFlow(false)
    val isShiny: StateFlow<Boolean> = _isShiny

    private val _pokemonInfo = MutableStateFlow<List<PokemonDetailItem>>(listOf())
    val pokemonInfo : StateFlow<List<PokemonDetailItem>> = _pokemonInfo

    private val _selectPage = MutableStateFlow(0)
    val selectPage: StateFlow<Int> = _selectPage

    fun fetchPokemonDetailInfo(number: String) {
        repository
            .fetchPokemonDetailInfo(number)
            .onEach { _pokemonInfo.value = it.toPokemonDetailItems() }
            .launchIn(viewModelScope)
    }

    fun insertPokemonCounter(number: String) = viewModelScope.launch {
        repository.insertPokemonCounter(number)
    }

    fun toggleIsShiny(): Boolean {
        _isShiny.value = _isShiny.value.not()
        return _isShiny.value
    }

    fun updatePage(page: Int) {
        _selectPage.value = page
    }

}