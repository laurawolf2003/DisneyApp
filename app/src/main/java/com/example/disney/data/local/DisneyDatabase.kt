package com.example.disney.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.disney.model.DisneyCharacter
import androidx.room.TypeConverters

@Database(
    entities = [DisneyCharacter::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class) //
abstract class DisneyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}