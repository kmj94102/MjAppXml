package com.example.mjappxml.ui.game.pokemon.dex

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.communication.model.PokemonSummary
import com.example.communication.repository.PokemonRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.customCatch
import com.example.mjappxml.model.PokemonSearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonDexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private var _isShiny = MutableStateFlow(false)
    val isShiny: StateFlow<Boolean> = _isShiny

    private val _pokemonList = MutableStateFlow(listOf<PokemonSummary>())
    val pokemonList: StateFlow<List<PokemonSummary>> = _pokemonList

    private val _searchInfo = MutableStateFlow(PokemonSearchItem.init())
    val searchInfo: StateFlow<PokemonSearchItem> = _searchInfo

    init {
        fetchPokemonList()
    }

    fun updateSearchInfo(item: PokemonSearchItem) {
        _searchInfo.value = item
        fetchPokemonList(true)
    }

    fun fetchPokemonList(isNeedClear: Boolean = false) {
        val searchInfo = searchInfo.value
        if (searchInfo.isMore.not()) return

        repository
            .fetchPokemonList(
                name = searchInfo.name,
                skip = searchInfo.skip,
                limit = searchInfo.limit,
                generations = searchInfo.getGenerationsInfo(),
                types = searchInfo.getTypesInfo(),
                isCatch = searchInfo.getIsCatch()
            )
            .setLoadingState()
            .onEach { (isMore, list) ->
                _searchInfo.update {
                    it.copy(isMore = isMore, skip = it.skip + 1)
                }
                Log.e("+++++", "${searchInfo.name} / $isMore")
                _pokemonList.value = if (isNeedClear) list else _pokemonList.value + list
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