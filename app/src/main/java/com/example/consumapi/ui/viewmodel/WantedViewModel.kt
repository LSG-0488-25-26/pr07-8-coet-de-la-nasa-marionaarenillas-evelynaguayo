package com.example.consumapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.consumapi.data.local.WantedEntity
import com.example.consumapi.data.model.WantedPerson
import com.example.consumapi.data.repository.FBIRepository
import kotlinx.coroutines.launch

// Gestiona l'estat de la UI i comunica la intericie amb el repository
class WantedViewModel(
    private val repository: FBIRepository
) : ViewModel() {

    private val _wantedList = MutableLiveData<List<WantedPerson>>()
    val wantedList: LiveData<List<WantedPerson>> = _wantedList

    private val _uiState = MutableLiveData<UIState>(UIState.Loading)
    val uiState: LiveData<UIState> = _uiState

    // Llistat de capturats (Room) observable des de la UI
    val captured: LiveData<List<WantedEntity>> = repository.getCaptured().asLiveData()

    // Text que l'usuari escriu a la SearchBar
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    fun onSearchQueryChange(newValue: String) {
        _searchQuery.value = newValue
    }

    // Captura o allibera una persona (toggle) guardant-ho a Room
    fun toggleCapture(person: WantedPerson) {
        viewModelScope.launch {
            repository.toggleCaptured(person)
        }
    }

    // Llista filtrada segons el text de cerca
    val filteredWantedList: LiveData<List<WantedPerson>> =
        MediatorLiveData<List<WantedPerson>>().apply {

            fun update() {
                val query = _searchQuery.value.orEmpty().trim().lowercase()
                val list = _wantedList.value.orEmpty()

                value = if (query.isBlank()) {
                    list
                } else {
                    list.filter { person ->
                        person.title.orEmpty().lowercase().contains(query)
                    }
                }
            }

            // Quan canvia la llista (API), recalcula el filtrat
            addSource(_wantedList) { update() }

            // Quan canvia el text de cerca, recalcula el filtrat
            addSource(_searchQuery) { update() }
        }

    sealed class UIState {
        object Loading : UIState()
        object Success : UIState()
        data class Error(val message: String) : UIState()
    }

    init {
        loadWantedPersons()
    }

    fun loadWantedPersons() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading

            val result = repository.getWantedList()

            result.onSuccess { response ->
                _wantedList.value = response.items
                _uiState.value = UIState.Success
            }.onFailure { e ->
                _uiState.value = UIState.Error(e.localizedMessage ?: e.toString())
            }
        }
    }
}
