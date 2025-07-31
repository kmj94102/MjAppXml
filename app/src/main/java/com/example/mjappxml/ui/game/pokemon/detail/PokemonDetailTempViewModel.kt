package com.example.mjappxml.ui.game.pokemon.detail

import androidx.lifecycle.viewModelScope
import com.example.communication.model.GenerationUpdateParam
import com.example.communication.model.UpdatePokemonCatch
import com.example.communication.repository.PokemonRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.customCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailTempViewModel @Inject constructor(
    private val repository: PokemonRepository
): BaseViewModel(){

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

    fun updateIsCatch(
        number: String,
        isCatch: Boolean
    ) = viewModelScope.launch {
        repository.updatePokemonCatch(
            UpdatePokemonCatch(number = number, isCatch = isCatch)
        )
    }

    fun updateGenerationIsCatch(
        idx: Int,
        onUpdate: (Boolean) -> Unit
    ) {
        repository
            .updateGenerationIsCatch(
                GenerationUpdateParam(idx = idx)
            )
            .setLoadingState()
            .onEach(onUpdate)
            .customCatch(
                onNetworkError = { updateNetworkErrorState(true) },
                onError = { updateMessage(it ?: "업데이트 중 오류가 발생하였습니다.") }
            )
            .launchIn(viewModelScope)
    }

    fun toggleIsShiny(): Boolean {
        _isShiny.value = _isShiny.value.not()
        return _isShiny.value
    }

    fun updatePage(page: Int) {
        _selectPage.value = page
    }

}