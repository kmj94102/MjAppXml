package com.example.mjappxml.ui.game.pokemon.detail

import com.example.communication.model.EvolutionInfo
import com.example.communication.model.PokemonDetailInfo
import com.example.communication.model.TypeInfo

sealed class PokemonDetailItem(
    val name: String,
    val number: String
) {
    data class Detail(
        val currentNumber: String,
        val currentName: String,
        val weekList: List<Int>,
        val classAndCharacter: String,
        val typeList: List<TypeInfo>,
        val description: String,
        val currentImage: String,
        val currentShinyImage: String,
        val beforeNum: String,
        val beforeImage: String,
        val beforeShinyImage: String,
        val afterNum: String,
        val afterImage: String,
        val afterShinyImage: String
    ): PokemonDetailItem(currentName, currentNumber) {
        fun getFirstTypeInfo() = runCatching { typeList[0] }.getOrDefault(TypeInfo.Unknown)

        fun getSecondTypeInfo() = runCatching { typeList[1] }.getOrDefault(TypeInfo.Unknown)
    }

    data class Evolution(
        val currentNumber: String,
        val currentName: String,
        val status: String,
        val evolutionList: List<EvolutionInfo>
    ): PokemonDetailItem(currentName, currentNumber) {
        private fun getStatusList() = status.split(",")
        fun getHp() = runCatching { getStatusList()[0] }.getOrDefault(0)
        fun getAttack() = runCatching { getStatusList()[1] }.getOrDefault(0)
        fun getDefence() = runCatching { getStatusList()[2] }.getOrDefault(0)
        fun getSpecialAttack() = runCatching { getStatusList()[3] }.getOrDefault(0)
        fun getSpecialDefence() = runCatching { getStatusList()[4] }.getOrDefault(0)
        fun getSpeed() = runCatching { getStatusList()[5] }.getOrDefault(0)
    }
}

fun PokemonDetailInfo.toPokemonDetailItems(): List<PokemonDetailItem> =
    listOf(
        PokemonDetailItem.Detail(
            currentNumber = "No.${pokemonInfo.number}",
            currentName = pokemonInfo.name,
            weekList = getWeekImageList(),
            classAndCharacter = getClassAndCharacter(),
            typeList = getTyeInfoList(),
            description = pokemonInfo.description,
            currentImage = pokemonInfo.image,
            currentShinyImage = pokemonInfo.shinyImage,
            beforeNum = beforeInfo?.number ?: "",
            beforeImage = beforeInfo?.image ?: "",
            beforeShinyImage = beforeInfo?.shinyImage ?: "",
            afterNum = nextInfo?.number ?: "",
            afterImage = nextInfo?.image ?: "",
            afterShinyImage = nextInfo?.shinyImage ?: ""
        ),
        PokemonDetailItem.Evolution(
            currentNumber = "No.${pokemonInfo.number}",
            currentName = pokemonInfo.name,
            status = pokemonInfo.status,
            evolutionList = evolutionInfo
        )
    )