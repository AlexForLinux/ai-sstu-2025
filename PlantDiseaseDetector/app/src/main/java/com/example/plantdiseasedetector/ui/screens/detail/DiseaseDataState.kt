package com.example.plantdiseasedetector.ui.screens.detail

import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.DiseaseWithAdvice

sealed class DiseaseDataState {
    object Loading : DiseaseDataState()
    data class Success(val item: DiseaseWithAdvice) : DiseaseDataState()
    data class Error(val message: String) : DiseaseDataState()
}