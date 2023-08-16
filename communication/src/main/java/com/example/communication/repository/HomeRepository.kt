package com.example.communication.repository

import com.example.communication.model.ElswordCounterUpdateItem
import com.example.communication.model.HomeInfoResult
import com.example.communication.model.HomeParam
import com.example.communication.model.UpdatePokemonCatch
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchHomeInfo(item: HomeParam): Flow<HomeInfoResult>

    suspend fun deleteSchedule(id: Int)

    suspend fun deletePlanTasks(id: Int)

    suspend fun updateCounter(count: Int, number: String)

    suspend fun deletePokemonCounter(number: String)

    suspend fun updateCatch(number: String)

    /** 포켓몬 잡은 상태 업데이트 **/
    suspend fun updatePokemonCatch(pokemonCatch: UpdatePokemonCatch): Result<String>

    suspend fun updateCustomIncrease(customIncrease: Int, number: String)

    suspend fun updateQuestCounter(item: ElswordCounterUpdateItem): Int

}
