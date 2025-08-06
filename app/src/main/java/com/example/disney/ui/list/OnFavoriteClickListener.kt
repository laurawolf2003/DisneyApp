package com.example.disney.ui.list

import com.example.disney.model.DisneyCharacter

interface OnFavoriteClickListener {
    fun onFavoriteClick(character: DisneyCharacter)
}