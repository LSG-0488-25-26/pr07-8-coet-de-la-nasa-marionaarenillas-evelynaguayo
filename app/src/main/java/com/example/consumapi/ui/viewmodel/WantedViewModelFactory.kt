package com.example.consumapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.consumapi.data.repository.FBIRepository

// Factory que permet crear el WantedViewModel injectant el repository
class WantedViewModelFactory(
    private val repository: FBIRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WantedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WantedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}