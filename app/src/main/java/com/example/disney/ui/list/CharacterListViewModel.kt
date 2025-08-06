package com.example.disney.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disney.data.repository.CharacterRepository
import com.example.disney.model.DisneyCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredCharacters: StateFlow<List<DisneyCharacter>> = combine(
        characters, searchQuery
    ) { list, query ->
        if (query.isBlank()) list
        else list.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _favorites = MutableStateFlow<List<DisneyCharacter>>(emptyList())
    val favorites: StateFlow<List<DisneyCharacter>> = _favorites

    init {
        loadCharacters()
        loadFavorites()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.refreshCharacters()
            repository.getCharacters().collect { characters ->
                _characters.value = characters.sortedBy { it.name }
                _isLoading.value = false
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(character: DisneyCharacter) {
        viewModelScope.launch {
            repository.setFavorite(character._id, !character.isFavorite)
            loadFavorites()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteCharacters().collect { favs ->
                _favorites.value = favs.sortedBy { it.name }
            }
        }
    }
}