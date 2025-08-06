package com.example.disney.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.disney.model.DisneyCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<DisneyCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<DisneyCharacter>)

    @Query("SELECT * FROM characters WHERE _id = :id")
    suspend fun getCharacterById(id: Int): DisneyCharacter?

    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE _id = :id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    fun getFavoriteCharacters(): Flow<List<DisneyCharacter>>
}