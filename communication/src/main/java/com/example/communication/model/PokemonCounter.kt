package com.example.communication.model

import com.example.communication.util.formatAmount

data class PokemonCounter(
    val index: Int,
    val number: String,
    val image: String,
    val shinyImage: String,
    val count: Int,
    val customIncrease: Int = 10,
    val isSelect: Boolean
) {
    fun getFormatCount() = count.formatAmount()

    companion object {
        fun init() = PokemonCounter(
            index = 0,
            number = "",
            image = "",
            shinyImage = "",
            count = 0,
            isSelect = false
        )
    }
}