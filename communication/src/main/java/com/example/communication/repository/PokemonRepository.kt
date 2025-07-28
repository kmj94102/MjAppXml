package com.example.communication.repository

import com.example.communication.model.BriefPokemonItem
import com.example.communication.model.GenerationCount
import com.example.communication.model.GenerationDex
import com.example.communication.model.GenerationUpdateParam
import com.example.communication.model.PokemonCounter
import com.example.communication.model.PokemonDetailInfo
import com.example.communication.model.PokemonEvolution
import com.example.communication.model.PokemonSpotlightItem
import com.example.communication.model.PokemonSummary
import com.example.communication.model.UpdatePokemonCatch
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    /** 포켓몬 도감 조회 **/
    fun fetchPokemonList(
        name: String,
        skip: Int,
        limit: Int,
        generations: String,
        types: String,
        isCatch: String
    ): Flow<Pair<Boolean, List<PokemonSummary>>>

    /** 포켓몬 상세 조회 **/
    fun fetchPokemonDetailInfo(number: String): Flow<PokemonDetailInfo>

    /** 포켓몬 잡은 상태 업데이트 **/
    suspend fun updatePokemonCatch(pokemonCatch: UpdatePokemonCatch): Result<String>

    /** 포켓몬 카운터 추가 **/
    suspend fun insertPokemonCounter(pokemonDetailInfo: PokemonDetailInfo): Result<Unit>

    suspend fun insertPokemonCounter(number: String)

    /** 포켓몬 카운터 조회 **/
    fun fetchPokemonCounter(): Flow<List<PokemonCounter>>

    /** 완료된 포켓몬 카운터 조회 **/
    fun fetchCompletedPokemonCounter(): Flow<List<PokemonCounter>>

    /** 포켓몬 카운터 삭제 **/
    suspend fun deletePokemonCounter(number: String)
    suspend fun deletePokemonCounter(index: Int)

    /** 카운터 업데이트 **/
    suspend fun updateCounter(count: Int, number: String)

    /** 증가 폭 업데이트 **/
    suspend fun updateCustomIncrease(customIncrease: Int, number: String)

    /** 잡기 상태 업데이트 **/
    suspend fun updateCatch(number: String)

    /** 카운터 복구 업데이트 **/
    suspend fun updateRestore(number: String, index: Int)

    /** 포켓몬 간략한 조회 (진화 추가용) **/
    fun fetchBriefPokemonList(search: String): Flow<List<BriefPokemonItem>>

    /** 포켓몬 진화 추가 **/
    suspend fun insertPokemonEvolution(evolutions: List<PokemonEvolution>): Result<String>

    /** 포켓몬 기존 spotlight 조회 **/
    fun fetchPokemonBeforeSpotlights(): Flow<List<PokemonSpotlightItem>>

    /** 포켓몬 spotlight 업데이트 **/
    suspend fun updatePokemonSpotlight(
        item: PokemonSpotlightItem
    ): String

    /** 포켓몬 게임 타이틀 조회 **/
    fun fetchGenerationTitleList(): Flow<List<GenerationCount>>

    /** 선택한 타이틀 도감 포켓몬 리스트 **/
    fun fetchGenerationList(index: Int): Flow<List<GenerationDex>>

    /** 잡은 상태 업데이트 **/
    fun updateGenerationIsCatch(item: GenerationUpdateParam): Flow<Boolean>
}
