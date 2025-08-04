package com.example.disney.data.remote

import com.example.disney.model.DisneyApiResponse
import retrofit2.http.GET

interface DisneyApi {
    @GET("character")  // <-- wichtig: nicht "characters"
    suspend fun getCharacters(): DisneyApiResponse
}
