package com.example.disney.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.disney.model.DisneyCharacter

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<DisneyCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<DisneyCharacter>)

    @Query("SELECT * FROM characters WHERE _id = :id")
    suspend fun getCharacterById(id: Int): DisneyCharacter?

}