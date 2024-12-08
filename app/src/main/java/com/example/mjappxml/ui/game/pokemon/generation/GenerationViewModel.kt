package com.example.mjappxml.ui.game.pokemon.generation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.communication.model.GenerationCount
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
class GenerationViewModel @Inject constructor(
    val repository: PokemonRepository
) : BaseViewModel() {

    private val _list = MutableStateFlow(listOf<GenerationCount>())
    val list: StateFlow<List<GenerationCount>> = _list

    init {
        fetchGenerationTitleList()
    }

    private fun fetchGenerationTitleList() {
        repository
            .fetchGenerationTitleList()
            .setLoadingState()
            .onEach {
                _list.value = it
            }
            .customCatch(
                onError = { updateMessage(it ?: "조회 중 오류가 발생하였습니다.") },
                onNetworkError = { updateNetworkErrorState(true) }
            )
            .launchIn(viewModelScope)
    }

}