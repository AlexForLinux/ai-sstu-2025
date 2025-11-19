package com.example.plantdiseasedetector.ui.screens.classify

import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.Prediction
import com.example.plantdiseasedetector.ui.screens.detail.DiseaseDataState

sealed class PredictionDataState {
    object EmptyData : PredictionDataState()
    object Loading : PredictionDataState()
    data class Success(val item: List<Prediction>) : PredictionDataState()
    data class Error(val message: String) : PredictionDataState()
}