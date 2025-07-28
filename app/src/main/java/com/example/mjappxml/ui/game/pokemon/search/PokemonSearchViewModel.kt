package com.example.mjappxml.ui.game.pokemon.search

import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.model.PokemonSearchGeneration
import com.example.mjappxml.model.PokemonSearchItem
import com.example.mjappxml.model.PokemonSearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(

) : BaseViewModel() {

    private val _pokemonSearchItem = MutableStateFlow<PokemonSearchItem>(PokemonSearchItem.init())
    val pokemonSearchItem: StateFlow<PokemonSearchItem> = _pokemonSearchItem

    fun updateIsCatch(isCatch: Boolean) {
        _pokemonSearchItem.update {
            it.copy(isCatch = isCatch)
        }
    }

    fun updateTypeSelect(type: String) {
        _pokemonSearchItem.update {
            it.copy(types = setNewTypeList(_pokemonSearchItem.value.types, type))
        }
    }

    private fun setNewTypeList(
        currentList: List<PokemonSearchType>,
        selectType: String
    ): List<PokemonSearchType> {
        val maxSelect = 2
        val isAllClicked = selectType == "전체"

        return if (isAllClicked) {
            currentList.map { it.copy(isSelect = it.type == "전체") }
        } else {
            val updatedList = currentList.map {
                it.copy(
                    isSelect = when (it.type) {
                        "전체" -> false
                        selectType -> it.isSelect.not()
                        else -> it.isSelect
                    }
                )
            }

            val selectedCount = updatedList.count { it.isSelect }

            val limitedList = if (selectedCount > maxSelect) {
                updateMessage("타입은 최대 2개까지 선택 가능합니다")
                updatedList.map {
                    if (it.type == selectType) it.copy(isSelect = false) else it
                }
            } else {
                updatedList
            }

            return if (limitedList.none { it.type != "전체" && it.isSelect }) {
                limitedList.map { it.copy(isSelect = it.type == "전체") }
            } else {
                limitedList.map {
                    if (it.type == "전체") it.copy(isSelect = false) else it
                }
            }
        }
    }

    fun updateGenerationSelect(generation: String) {
        _pokemonSearchItem.update {
            it.copy(
                generations = setNewGenerationList(
                    _pokemonSearchItem.value.generations,
                    generation
                )
            )
        }
    }

    private fun setNewGenerationList(
        currentList: List<PokemonSearchGeneration>,
        selectGeneration: String
    ): List<PokemonSearchGeneration> {
        val isAllClicked = selectGeneration == "전체"

        return if (isAllClicked) {
            currentList.map { it.copy(isSelect = it.generation == "전체") }
        } else {
            val updateList = currentList.map {
                if (it.generation == selectGeneration) {
                    it.copy(isSelect = it.isSelect.not())
                } else if (it.generation == "전체") {
                    it.copy(isSelect = false)
                } else {
                    it
                }
            }
            if (updateList.count { it.isSelect } >= updateList.size - 1 ||
                updateList.none { it.isSelect }
            ) {
                currentList.map { it.copy(isSelect = it.generation == "전체") }
            } else {
                updateList
            }
        }
    }

    fun clear() {
        _pokemonSearchItem.value = PokemonSearchItem.init()
    }

}