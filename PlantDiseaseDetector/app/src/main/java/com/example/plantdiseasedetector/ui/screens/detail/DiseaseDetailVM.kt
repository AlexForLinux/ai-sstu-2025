package com.example.plantdiseasedetector.ui.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
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

    fun loadDisease(diseaseId: String?) {
        viewModelScope.launch {
            Log.i("TAG", diseaseId.toString())
            repository.getDiseaseById(diseaseId)
            .catch { exception ->
                _diseaseState.value = DiseaseDataState.Error(
                    message = "Не удалось загрузить данные: ${exception.message}"
                )
            }
            .collect { disease ->
                if (disease != null) {
                    _diseaseState.value = DiseaseDataState.Success(disease)
                } else {
                    _diseaseState.value = DiseaseDataState.Error("Страница не найдена")
                }
            }
        }
    }
}