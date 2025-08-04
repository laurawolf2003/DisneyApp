package com.example.disney.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disney.data.repository.CharacterRepository
import com.example.disney.model.DisneyCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _character = MutableStateFlow<DisneyCharacter?>(null)
    val character: StateFlow<DisneyCharacter?> = _character

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _character.value = repository.getCharacterById(id)
            _isLoading.value = false
        }
    }
}