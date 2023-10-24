package com.example.communication.repository

import com.example.communication.model.BriefPokemonItem
import com.example.communication.model.PokemonCounter
import com.example.communication.model.PokemonDetailInfo
import com.example.communication.model.PokemonEvolution
import com.example.communication.model.PokemonSpotlightItem
import com.example.communication.model.UpdatePokemonCatch
import com.example.communication.model.getFailureThrow
import com.example.communication.model.printStackTrace
import com.example.communication.service.PokemonClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val client: PokemonClient,
) : PokemonRepository {

    /** 포켓몬 도감 조회 **/
    override fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
    ) = flow {
        client.fetchPokemonList(name, skip * limit, limit)
            .onSuccess {
                val isMoreData = it.getIsMoreData(skip * limit)
                val list = it.getMappingList()
                emit(Pair(isMoreData, list))
            }
            .getFailureThrow()
    }

    /** 포켓몬 상세 조회 **/
    override fun fetchPokemonDetailInfo(number: String) = flow {
        client.fetchPokemonDetailInfo(number)
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 포켓몬 잡은 상태 업데이트 **/
    override suspend fun updatePokemonCatch(
        pokemonCatch: UpdatePokemonCatch
    ) = client.updatePokemonCatch(pokemonCatch).getFailureThrow()

    /** 포켓몬 카운터 추가 **/
    override suspend fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo) =
        client.insertPokemonCounter(pokemonDetailInfo.toPokemonCounterEntity())

    override suspend fun insertPokemonCounter(number: String) {
        client
            .fetchPokemonWithNumber(number)
            .onSuccess { client.insertPokemonCounter(it.toPokemonCounterEntity()) }
            .getFailureThrow()
    }

    /** 포켓몬 카운터 조회 **/
    override fun fetchPokemonCounter(): Flow<List<PokemonCounter>> =
        client
            .fetchPokemonCounter()
            .getOrThrow()

    /** 완료된 포켓몬 카운터 조회 **/
    override fun fetchCompletedPokemonCounter(): Flow<List<PokemonCounter>> =
        client
            .fetchCompletedPokemonCounter()
            .getOrThrow()

    /** 포켓몬 카운터 삭제 **/
    override suspend fun deletePokemonCounter(number: String) {
        client.deletePokemonCounter(number).printStackTrace()
    }
    override suspend fun deletePokemonCounter(index: Int) {
        client.deletePokemonCounter(index).printStackTrace()
    }

    /** 카운터 업데이트 **/
    override suspend fun updateCounter(count: Int, number: String) {
        client.updateCounter(count, number).printStackTrace()
    }

    /** 증가 폭 업데이트 **/
    override suspend fun updateCustomIncrease(customIncrease: Int, number: String) {
        client.updateCustomIncrease(customIncrease, number).printStackTrace()
    }

    /** 잡기 상태 업데이트 **/
    override suspend fun updateCatch(number: String) {
        client.updateCatch(number).printStackTrace()
    }

    /** 카운터 복구 업데이트 **/
    override suspend fun updateRestore(number: String, index: Int) {
        client.updateRestore(index).printStackTrace()
        client.updatePokemonCatch(UpdatePokemonCatch(number, false)).getFailureThrow()
    }

    /** 포켓몬 간략한 조회 (진화 추가용) **/
    override fun fetchBriefPokemonList(search: String): Flow<List<BriefPokemonItem>> = flow {
        client
            .fetchBriefPokemonList(search)
            .onSuccess { list ->
                emit(list.mapNotNull { it.toBriefPokemonItem() })
            }
            .getFailureThrow()
    }

    /** 포켓몬 진화 추가 **/
    override suspend fun insertPokemonEvolution(evolutions: List<PokemonEvolution>) =
        client.insertPokemonEvolution(evolutions).getFailureThrow()

    /** 포켓몬 기존 spotlight 조회 **/
    override fun fetchPokemonBeforeSpotlights() = flow {
        client
            .fetchPokemonBeforeSpotlights()
            .onSuccess { emit(it) }
            .getFailureThrow()
    }

    /** 포켓몬 spotlight 업데이트 **/
    override suspend fun updatePokemonSpotlight(item: PokemonSpotlightItem) =
        client
            .updatePokemonSpotlight(item)
            .printStackTrace()
            .getOrElse { "업데이트 실패" }
}