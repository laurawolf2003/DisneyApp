package com.example.disney.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.disney.model.DisneyCharacter
import com.example.disney.data.remote.DisneyApi
import com.example.disney.data.local.CharacterDao


class CharacterRepository(
    private val api: DisneyApi,
    private val dao: CharacterDao
) {
    suspend fun refreshCharacters() {
        try {
            val characters = api.getCharacters()
            dao.insertAll(characters)
        } catch (e: Exception) {
            // Handle error (show cached data)
        }
    }

    fun getCharacters(): Flow<List<DisneyCharacter>> = flow {
        emit(dao.getAllCharacters())
    }

    suspend fun getCharacterById(id: Int): DisneyCharacter? {
        return dao.getCharacterById(id)
    }
}