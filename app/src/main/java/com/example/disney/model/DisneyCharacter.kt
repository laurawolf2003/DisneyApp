package com.example.disney.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.disney.data.local.Converters

@Entity(tableName = "characters")
@TypeConverters(Converters::class) // Converter hinzufügen
data class DisneyCharacter(
    @PrimaryKey val _id: Int,
    val name: String,
    val imageUrl: String?,
    val films: List<String>?,
    val tvShows: List<String>?,
    val videoGames: List<String>?,
    val allies: List<String>?,
    val enemies: List<String>?,
    val url: String
)