package com.example.disney.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.disney.data.repository.CharacterRepository
import com.example.disney.model.DisneyCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _characters = MutableStateFlow<List<DisneyCharacter>>(emptyList())
    val characters: StateFlow<List<DisneyCharacter>> = _characters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.refreshCharacters()
            repository.getCharacters().collect { characters ->
                _characters.value = characters
                _isLoading.value = false
            }
        }
    }
}