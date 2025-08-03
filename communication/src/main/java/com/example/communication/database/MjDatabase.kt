package com.example.communication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.communication.database.dao.PokemonDao
import com.example.communication.database.entity.PokemonCounterEntity

@Database(
    entities = [
        PokemonCounterEntity::class,
    ],
    version = 3,
    exportSchema = true
)
abstract class MjDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE PokemonCounterEntity ADD COLUMN isSelect INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}