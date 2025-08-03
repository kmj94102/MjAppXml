package com.example.communication.di

import android.app.Application
import androidx.room.Room
import com.example.communication.database.MjDatabase
import com.example.communication.database.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): MjDatabase = Room
        .databaseBuilder(application, MjDatabase::class.java, "mj_database.db")
        .fallbackToDestructiveMigration()
        .addMigrations(MjDatabase.MIGRATION_2_3)
        .build()

    @Provides
    @Singleton
    fun providePokemonDao(
        database: MjDatabase
    ): PokemonDao = database.pokemonDao()
}