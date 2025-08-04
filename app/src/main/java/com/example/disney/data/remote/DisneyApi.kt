package com.example.disney.data.remote

import com.example.disney.model.DisneyCharacter
import retrofit2.http.GET

interface DisneyApi {
    @GET("characters")
    suspend fun getCharacters(): List<DisneyCharacter>
}