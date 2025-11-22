package com.example.plantdiseasedetector.ui.screens.catalog

import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.ModelPrediction
import com.example.plantdiseasedetector.ui.screens.classify.PredictionDataState

sealed class DiseaseListState {
    object Loading : DiseaseListState()
    data class Success(val diseases: List<Disease>) : DiseaseListState()
    data class Error(val message: String) : DiseaseListState()
}