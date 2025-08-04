package com.example.mjappxml.model
import android.os.Parcelable
import com.example.communication.model.TypeInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonSearchItem(
    var name: String,
    val isCatch: Boolean,
    val types: List<PokemonSearchType>,
    val generations: List<PokemonSearchGeneration>,
    val isMore: Boolean = true,
    val skip: Int = 0,
    val limit: Int = 100
): Parcelable {

    fun getGenerationsInfo() = generations
        .filter { it.isSelect }
        .joinToString(",") {
            it.generation.replace("세대", "")
        }
        .isAllOrElse()

    fun getTypesInfo() = types
        .filter { it.isSelect }
        .joinToString(",") { it.type }
        .isAllOrElse()

    fun getIsCatch() = if (isCatch) "all" else "false"

    private fun String.isAllOrElse() = if ("전체" == this) "" else this

    fun getConditions(): List<String> {
        val results = mutableListOf<String>()

        if (name.isNotEmpty()) {
            results.add("%$name%")
        }

        val types = types
            .filter { it.type != "전체" }
            .filter { it.isSelect }
            .map { it.type }
        val generations = generations
            .filter { it.generation != "전체" }
            .filter { it.isSelect }
            .map { it.generation }

        results.addAll(types)
        results.addAll(generations)

        if (isCatch.not()) {
            results.add("안 잡은 포켓몬 만")
        }

        return results
    }

    companion object {
        fun init() = PokemonSearchItem(
            name = "",
            isCatch = true,
            types = listOf(
                PokemonSearchType("전체", true)
            ) + TypeInfo.entries.map { PokemonSearchType(it.koreanName) }.dropLast(1),
            generations = listOf(
                PokemonSearchGeneration("전체", true),
                PokemonSearchGeneration("1세대"),
                PokemonSearchGeneration("2세대"),
                PokemonSearchGeneration("3세대"),
                PokemonSearchGeneration("4세대"),
                PokemonSearchGeneration("5세대"),
                PokemonSearchGeneration("6세대"),
                PokemonSearchGeneration("7세대"),
                PokemonSearchGeneration("8세대"),
                PokemonSearchGeneration("9세대"),
                PokemonSearchGeneration("레전드 아르세우스"),
                PokemonSearchGeneration("레전드 ZA"),
            )
        )
    }
}

@Parcelize
data class PokemonSearchType(
    val type: String,
    val isSelect: Boolean = false
): Parcelable

@Parcelize
data class PokemonSearchGeneration(
    val generation: String,
    val isSelect: Boolean = false
): Parcelable