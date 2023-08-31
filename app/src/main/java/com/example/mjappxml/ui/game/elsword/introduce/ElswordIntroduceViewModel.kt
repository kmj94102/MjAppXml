package com.example.mjappxml.ui.game.elsword.introduce

import com.example.mjappxml.BaseViewModel
import com.example.mjappxml.model.ElswordCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ElswordIntroduceViewModel @Inject constructor(): BaseViewModel() {
    private val list = ElswordCharacters.values()
    private var index = 0

    private val _selectItem = MutableStateFlow(list[index])
    val selectItem: StateFlow<ElswordCharacters> = _selectItem

    fun onNextItem() {
        if (index < list.lastIndex) {
            _selectItem.value = list[++index]
        } else {
            index = 0
            _selectItem.value = list[index]
        }
    }

    fun onPrevItem() {
        if (index > 0) {
            _selectItem.value = list[--index]
        } else {
            index = list.lastIndex
            _selectItem.value = list[index]
        }
    }

}