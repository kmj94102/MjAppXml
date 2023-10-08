package com.example.mjappxml.ui.game.pokemon.dex

import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonSummary
import com.example.communication.repository.PokemonRepository
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
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private var _isShiny = MutableStateFlow(false)
    val isShiny: StateFlow<Boolean> = _isShiny

    private val _pokemonList = MutableStateFlow(listOf<PokemonSummary>())
    val pokemonList: StateFlow<List<PokemonSummary>> = _pokemonList

    private var isMore = true
    private var skip = 0
    private val limit = 100
    private var name = ""

    init {
        fetchPokemonList()
    }

    fun toggleIsShiny() {
        _isShiny.value = _isShiny.value.not()
    }

    fun updateName(name: String) {
        _pokemonList.value = listOf()
        this.name = name
        skip = 0
        isMore = true
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        if (isMore.not()) return

        repository
            .fetchPokemonList(
                name = name,
                skip = skip,
                limit = limit
            )
            .setLoadingState()
            .onEach { (isMore, list) ->
                this.isMore = isMore
                skip += 1
                _pokemonList.value = _pokemonList.value + list
                updateNetworkErrorState(false)
            }
            .customCatch(
                onError = { updateMessage(it ?: "조회 중 오류가 발생하였습니다.") },
                onNetworkError = { updateNetworkErrorState(true) }
            )
            .launchIn(viewModelScope)
    }

    fun updateIsCatch(
        number: String,
        isCatch: Boolean
    ) {
        _pokemonList.value = _pokemonList.value.map { pokemon ->
            if (pokemon.number == number) {
                pokemon.copy(isCatch = isCatch)
            } else {
                pokemon
            }
        }
    }

}