package com.example.communication.di

import com.example.communication.repository.AccountBookRepository
import com.example.communication.repository.AccountBookRepositoryImpl
import com.example.communication.repository.CalendarRepository
import com.example.communication.repository.CalendarRepositoryImpl
import com.example.communication.repository.ElswordRepository
import com.example.communication.repository.ElswordRepositoryImpl
import com.example.communication.repository.HomeRepository
import com.example.communication.repository.HomeRepositoryImpl
import com.example.communication.repository.PokemonRepository
import com.example.communication.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    fun bindElswordRepository(
        elswordRepositoryImpl: ElswordRepositoryImpl
    ): ElswordRepository

    @Binds
    fun bindCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository

    @Binds
    fun bindAccountBookRepository(
        accountBookRepositoryImpl: AccountBookRepositoryImpl
    ): AccountBookRepository

    @Binds
    fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

}