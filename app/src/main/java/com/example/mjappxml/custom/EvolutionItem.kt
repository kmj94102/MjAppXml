package com.example.mjappxml.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.communication.model.EvolutionInfo
import com.example.mjappxml.R
import com.example.mjappxml.databinding.CustomEvolutionItemBinding

class EvolutionItemView : ConstraintLayout {

    private var evolutionInfo = EvolutionInfo.init()

    private val binding by lazy {
        CustomEvolutionItemBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_evolution_item, this, false)
        )
    }

    private val _isShiny = MutableLiveData(false)
    val isShiny: LiveData<Boolean> = _isShiny

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) :
            super(context, attributeSet, defStyle)

    init {
        addView(binding.root)
    }

    fun setEvolutionInfo(info: EvolutionInfo) {
        evolutionInfo = info
        binding.info = evolutionInfo
        binding.item = this
        binding.invalidateAll()
    }

    fun updateShinyState(value: Boolean) {
        _isShiny.value = value
        binding.invalidateAll()
    }

    fun setBeforeClickListener(onClick: (String) -> Unit) {
        binding.cardBeforePokemon.setOnClickListener {
            onClick(evolutionInfo.beforeNumber)
        }
    }

    fun setAfterClickListener(onClick: (String) -> Unit) {
        binding.cardAfterPokemon.setOnClickListener {
            onClick(evolutionInfo.afterNumber)
        }
    }



}