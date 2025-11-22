package com.example.plantdiseasedetector.ui.screens.detail

import com.example.plantdiseasedetector.data.model.Disease

sealed class DiseaseDataState {
    object Loading : DiseaseDataState()
    data class Success(val item: Disease) : DiseaseDataState()
    data class Error(val message: String) : DiseaseDataState()
}