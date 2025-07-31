package com.example.communication.model

import com.example.communication.database.entity.PokemonCounterEntity
import java.util.Calendar

data class PokemonInfo(
    val number: String = "",
    val name: String = "",
    val status: String = "",
    val classification: String = "",
    val characteristic: String = "",
    val attribute: String = "",
    val image: String = "",
    val shinyImage: String = "",
    val spotlight: String = "",
    val description: String = "",
    val generation: Int = 0,
    val isCatch: Boolean = false
) {
    fun toPokemonCounterEntity() = PokemonCounterEntity(
        number = number,
        image = image,
        shinyImage = shinyImage,
        count = 0,
        timestamp = Calendar.getInstance().timeInMillis
    )
}

data class EvolutionInfo(
    val beforeDot: String,
    val beforeShinyDot: String,
    val beforeNumber: String,
    val afterDot: String,
    val afterShinyDot: String,
    val afterNumber: String,
    val evolutionImage: String,
    val evolutionCondition: String
) {
    companion object {
        fun init() = EvolutionInfo(
            beforeDot = "",
            beforeShinyDot = "",
            beforeNumber = "",
            afterDot = "",
            afterShinyDot = "",
            afterNumber = "",
            evolutionImage = "",
            evolutionCondition = ""
        )
    }
}

data class PokemonImageInfo(
    val number: String,
    val image: String,
    val shinyImage: String
)

data class PokemonDetailInfo(
    val pokemonInfo: PokemonInfo = PokemonInfo(),
    val beforeInfo: PokemonImageInfo? = null,
    val nextInfo: PokemonImageInfo? = null,
    val evolutionInfo: List<EvolutionInfo> = listOf(),
) {
    fun getWeekImageList(): List<Int> {
        val attributeList = pokemonInfo.attribute
            .split(",")
            .map { getWeaknessInfo(it) }

        val weekList = when (attributeList.size) {
            1 -> {
                attributeList[0]
            }

            2 -> {
                attributeList[0].zip(attributeList[1]).map { it.first * it.second }
            }

            else -> emptyList()
        }

        return weekList
            .zip(TypeInfo.entries.map { it.imageRes })
            .filter { it.first >= 2f }
            .map { it.second }
    }

    fun getWeakInfoList(): List<PokemonWeakInfo> {
        val attributeList = pokemonInfo.attribute
            .split(",")
            .map { getWeaknessInfo(it) }

        val weekList = when (attributeList.size) {
            1 -> {
                attributeList[0]
            }

            2 -> {
                attributeList[0].zip(attributeList[1]).map { it.first * it.second }
            }

            else -> emptyList()
        }.zip(TypeInfo.entries.map { it.imageRes })

        val grouped = weekList.groupBy { (value, _) ->
            when {
                value > 1f -> "효과가 좋다"
                value == 1f -> "보통"
                value == 0f -> "효과가 없다"
                else -> "효과가 별로다"
            }
        }


        return listOf(
            PokemonWeakInfo("효과가 좋다", grouped["효과가 좋다"]?.map { it.second }),
            PokemonWeakInfo("보통", grouped["보통"]?.map { it.second }),
            PokemonWeakInfo("효과가 없다", grouped["효과가 없다"]?.map { it.second }),
            PokemonWeakInfo("효과가 별로다", grouped["효과가 별로다"]?.map { it.second }),
        ).filter { it.imageRes != null }
    }

    fun getTyeInfoList() = pokemonInfo.attribute
        .split(",")
        .map { getTypeInfo(it) }

    fun isSingleType() = pokemonInfo.attribute.split(",").size <= 1
    fun firstType() = getTypeInfo(pokemonInfo.attribute.split(",").firstOrNull() ?: "")
    fun secondType() = getTypeInfo(pokemonInfo.attribute.split(",").lastOrNull() ?: "")

    fun getHp() = pokemonInfo.status.split(",").getOrNull(0)
    fun getAttack() = pokemonInfo.status.split(",").getOrNull(1)
    fun getDefence() = pokemonInfo.status.split(",").getOrNull(2)
    fun getSpacialAttack() = pokemonInfo.status.split(",").getOrNull(3)
    fun getSpacialDefence() = pokemonInfo.status.split(",").getOrNull(4)
    fun getSpeed() = pokemonInfo.status.split(",").getOrNull(5)

    fun getClassAndCharacter() =
        "${pokemonInfo.classification} | ${pokemonInfo.characteristic.replace(",", ", ")}"

    fun toPokemonCounterEntity() = PokemonCounterEntity(
        number = pokemonInfo.number,
        image = pokemonInfo.image,
        shinyImage = pokemonInfo.shinyImage,
        count = 0,
        timestamp = Calendar.getInstance().timeInMillis
    )
}

data class PokemonWeakInfo(
    val title: String,
    val imageRes: List<Int>?
)