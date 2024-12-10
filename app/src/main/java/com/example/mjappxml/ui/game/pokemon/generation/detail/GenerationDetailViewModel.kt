package com.example.mjappxml.ui.game.pokemon.generation.detail

import androidx.lifecycle.viewModelScope
import com.example.communication.model.GenerationDex
import com.example.communication.repository.PokemonRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.customCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenerationDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : BaseViewModel() {

    private val _pokemonList = MutableStateFlow(listOf<GenerationDex>())
    val pokemonList: StateFlow<List<GenerationDex>> = _pokemonList

    fun fetchGenerationList(index: Int) {
        repository
            .fetchGenerationList(index)
            .setLoadingState()
            .onEach { _pokemonList.value = it }
            .customCatch(
                onError = { updateFinish(true) },
                onNetworkError = { updateNetworkErrorState(true) }
            )
            .launchIn(viewModelScope)
    }

}