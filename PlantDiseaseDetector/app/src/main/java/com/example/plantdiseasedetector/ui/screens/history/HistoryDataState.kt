package com.example.plantdiseasedetector.ui.screens.history

import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.ReportWithDetails

sealed class HistoryDataState {
    object Loading : HistoryDataState()
    data class Success(val reports: List<ReportWithDetails>) : HistoryDataState()
    data class Error(val message: String) : HistoryDataState()
}