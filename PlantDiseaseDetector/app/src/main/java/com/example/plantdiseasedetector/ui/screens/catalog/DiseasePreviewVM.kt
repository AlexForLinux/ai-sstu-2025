package com.example.plantdiseasedetector.ui.screens.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.plantdiseasedetector.data.model.DiseasePreviewModel
import com.example.plantdiseasedetector.data.repository.DiseasePreviewRepository

class DiseasePreviewVM(
    private val repository: DiseasePreviewRepository
) : ViewModel() {
    var diseases by mutableStateOf<List<DiseasePreviewModel>>(emptyList())
        private set

    init {
        loadItems()
    }

    private fun loadItems() {
        diseases = repository.getDiseasePreviewList()
    }
}