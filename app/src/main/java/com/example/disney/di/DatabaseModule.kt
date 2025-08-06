package com.example.disney.di

import android.content.Context
import androidx.room.Room
import com.example.disney.data.local.DisneyDatabase
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
    fun provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext context: Context): DisneyDatabase {
        return Room.databaseBuilder(
            context,
            DisneyDatabase::class.java,
            "disney_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCharacterDao(database: DisneyDatabase) = database.characterDao()
}