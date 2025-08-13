package com.example.communication.model

data class HomeParam(
    val startDate: String,
    val endDate: String
)

data class HomeInfoResult(
    val calendarInfo: List<CalendarResult> = listOf(),
    val questInfo: List<ElswordCounter> = listOf(),
    val pokemonInfo: List<PokemonCounter> = listOf()
) {
    fun getScheduleList() =
        calendarInfo.map { it.scheduleInfoList + it.planInfoList }.flatten()
}