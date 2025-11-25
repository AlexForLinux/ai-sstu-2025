package com.example.plantdiseasedetector.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseDetailVM @Inject constructor (
    private val repository: DiseaseRepository
) : ViewModel() {

    private val _diseaseState = MutableStateFlow<DiseaseDataState>(DiseaseDataState.Loading)
    val diseaseState: StateFlow<DiseaseDataState> = _diseaseState.asStateFlow()

    fun setErrorState(message: String) {
        var fullMessage = "Ошибка страницы"

        if (!message.isEmpty()) {
            fullMessage += ": $message"
        }

        _diseaseState.value =
            DiseaseDataState.Error(fullMessage)
    }

    fun loadDisease(diseaseId: Long) {
        viewModelScope.launch {
            try {
                val disease = repository.getDiseaseWithAdviceById(diseaseId)
                _diseaseState.value = DiseaseDataState.Success(disease)
            } catch (e: Exception) {
                setErrorState("Не удалось загрузить сведения")
            }
        }
    }

    fun updateDiseaseMark(id: Long, isMarked: Boolean){
        viewModelScope.launch {
            try {
                repository.updateDiseaseMark(id, isMarked)
            } catch (e: Exception) {
                setErrorState("Не удалось добавить в закладки")
            }
        }
    }
}