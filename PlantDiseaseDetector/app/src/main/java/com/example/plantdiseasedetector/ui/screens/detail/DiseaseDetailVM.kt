package com.example.plantdiseasedetector.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import com.example.plantdiseasedetector.ui.screens.history.HistoryDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseDetailVM @Inject constructor (
    private val repository: DiseaseRepository
) : ViewModel() {

    private val _diseaseState = MutableStateFlow<DiseaseDataState>(DiseaseDataState.Loading)
    val diseaseState: StateFlow<DiseaseDataState> = _diseaseState.asStateFlow()

    fun setErrorState(message: String) {
        var fullMessage = "Не удалось загузить сведения"

        if (!message.isEmpty()) {
            fullMessage += ": $message"
        }

        _diseaseState.value =
            DiseaseDataState.Error(fullMessage)
    }

    fun loadDisease(diseaseId: Long) {
        viewModelScope.launch {
            try {
                val disease = repository.getDiseaseById(diseaseId)
                _diseaseState.value = DiseaseDataState.Success(disease)
            } catch (e: Exception) {
                setErrorState(e.message ?: "")
            }
        }
    }

    fun updateDiseaseMark(id: Long, isMarked: Boolean){
        viewModelScope.launch {
            repository.updateDiseaseMark(id, isMarked)
        }
    }
}