package com.example.plantdiseasedetector.ui.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DiseaseVM @Inject constructor (
    private val repository: DiseaseRepository
) : ViewModel() {
    val diseases: StateFlow<List<Disease>> = repository.getDiseases()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getDiseaseById(id: Int?): StateFlow<Disease?> = repository.getDiseaseById(id)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}