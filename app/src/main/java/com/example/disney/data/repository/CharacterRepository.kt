package com.example.disney.data.repository

import android.util.Log
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
            val characters = api.getCharacters().data
            Log.d("Repository", "Empfangene Charaktere: ${characters.size}")
            dao.insertAll(characters)
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Abrufen der Charaktere", e)
        }
    }

    fun getCharacters(): Flow<List<DisneyCharacter>> = flow {
        emit(dao.getAllCharacters())
    }

    suspend fun getCharacterById(id: Int): DisneyCharacter? {
        return dao.getCharacterById(id)
    }
}
