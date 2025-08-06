package com.example.disney.di

import com.example.disney.data.remote.DisneyApiService
import com.example.disney.data.repository.CharacterRepository
import com.example.disney.data.remote.DisneyApi
import com.example.disney.data.local.DisneyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDisneyApi(): DisneyApi = DisneyApiService.api

    @Singleton
    @Provides
    fun provideCharacterRepository(api: DisneyApi, db: DisneyDatabase): CharacterRepository {
        return CharacterRepository(api, db.characterDao())
    }
}