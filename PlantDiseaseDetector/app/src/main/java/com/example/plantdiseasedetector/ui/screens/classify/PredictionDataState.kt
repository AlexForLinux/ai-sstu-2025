package com.example.plantdiseasedetector.ui.screens.classify

import com.example.plantdiseasedetector.data.model.ModelPrediction

sealed class PredictionDataState {
    object EmptyData : PredictionDataState()
    object Loading : PredictionDataState()
    data class Success(val item: ModelPrediction) : PredictionDataState()
    data class Error(val message: String) : PredictionDataState()
}