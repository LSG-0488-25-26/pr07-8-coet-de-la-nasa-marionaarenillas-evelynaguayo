package com.example.consumapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consumapi.data.model.WantedPerson
import com.example.consumapi.data.repository.FBIRepository
import kotlinx.coroutines.launch

class WantedViewModel(
    private val repository: FBIRepository = FBIRepository()
) : ViewModel() {

    private val _wantedList = MutableLiveData<List<WantedPerson>>()
    val wantedList: LiveData<List<WantedPerson>> = _wantedList

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

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
