package com.example.disney.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import com.example.disney.model.DisneyCharacter
import com.example.disney.data.remote.DisneyApi
import com.example.disney.data.local.CharacterDao
import kotlinx.coroutines.flow.first

class CharacterRepository(
    private val api: DisneyApi,
    private val dao: CharacterDao
) {
    suspend fun refreshCharacters() {
        try {
            val characters = api.getCharacters().data
            Log.d("Repository", "Empfangene Charaktere: ${characters.size}")
            val currentFavorites = dao.getFavoriteCharacters().first().map { it._id }.toSet()
            val merged = characters.map { it.copy(isFavorite = currentFavorites.contains(it._id)) }
            dao.insertAll(merged)
        } catch (e: Exception) {
            Log.e("Repository", "Fehler beim Abrufen der Charaktere", e)
        }
    }

    fun getCharacters(): Flow<List<DisneyCharacter>> = dao.getAllCharacters()

    suspend fun getCharacterById(id: Int): DisneyCharacter? {
        return dao.getCharacterById(id)
    }

    suspend fun setFavorite(characterId: Int, isFavorite: Boolean) {
        dao.setFavorite(characterId, isFavorite)
    }

    fun getFavoriteCharacters(): Flow<List<DisneyCharacter>> = dao.getFavoriteCharacters()
}