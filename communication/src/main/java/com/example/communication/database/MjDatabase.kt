package com.example.communication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.communication.database.dao.PokemonDao
import com.example.communication.database.entity.PokemonCounterEntity

@Database(
    entities = [
        PokemonCounterEntity::class,
    ],
    version = 2,
    exportSchema = true
)
abstract class MjDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao


}