package com.example.plantdiseasedetector.ui.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DiseaseVM (
    private val repository: DiseaseRepository
) : ViewModel() {
    val diseases: StateFlow<List<Disease>> = repository.getDiseases()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getDiseaseById(id: Int?): StateFlow<Disease?> = repository.getDiseaseById(id)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}