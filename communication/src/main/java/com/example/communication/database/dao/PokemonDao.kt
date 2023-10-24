package com.example.communication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.communication.database.entity.PokemonCounterEntity
import com.example.communication.model.PokemonCounter
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonCounter(pokemonCounterEntity: PokemonCounterEntity)

    @Query("SELECT `index`, number, image, shinyImage, count, customIncrease FROM PokemonCounterEntity WHERE isCatch = 0")
    fun fetchPokemonCounter(): Flow<List<PokemonCounter>>

    @Query("SELECT `index`, number, image, shinyImage, count, customIncrease FROM PokemonCounterEntity WHERE isCatch = 1")
    fun fetchCompletedPokemonCounter(): Flow<List<PokemonCounter>>

    @Query("UPDATE PokemonCounterEntity SET count = :count WHERE number = :number")
    suspend fun updateCounter(count: Int, number: String)

    @Query("UPDATE PokemonCounterEntity SET customIncrease = :customIncrease WHERE number = :number")
    suspend fun updateCustomIncrease(customIncrease: Int, number: String)

    @Query("UPDATE PokemonCounterEntity SET isCatch = 1 WHERE number = :number")
    suspend fun updateCatch(number: String)

    @Query("UPDATE PokemonCounterEntity SET isCatch = 0 WHERE `index` = :index")
    suspend fun updateRestore(index: Int)

    @Query("DELETE FROM PokemonCounterEntity WHERE number = :number")
    suspend fun deleteCounter(number: String)

    @Query("DELETE FROM PokemonCounterEntity WHERE `index` = :index")
    suspend fun deleteCounter(index: Int)
}