package com.example.mjappxml.ui.game.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.communication.model.NetworkError
import com.example.communication.model.PokemonDetailInfo
import com.example.communication.repository.PokemonRepository
import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _info = repository
        .fetchPokemonDetailInfo(savedStateHandle.get<String>(Constants.NUMBER) ?: "")
        .setLoadingState()
        .catch {
            if (it is NetworkError) updateNetworkErrorState()
            else updateMessage(it.message ?: "포켓몬 정보를 가져오지 못하였습니다.")
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PokemonDetailInfo())

    val info: StateFlow<PokemonDetailInfo> = _info

    private val _isShiny = MutableStateFlow(false)
    val isShiny: StateFlow<Boolean> = _isShiny

    fun updateIsShiny() {
        _isShiny.value = !_isShiny.value
    }

    fun insertCounter() {
        repository
            .insertPokemonCounter(info.value.pokemonInfo.number)
            .setLoadingState()
            .onEach {
                updateMessage("${info.value.pokemonInfo.name} 등록 성공")
            }
            .catch {
                updateMessage("${info.value.pokemonInfo.name} 등록 실패")
            }
            .launchIn(viewModelScope)
    }

}