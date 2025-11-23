package com.example.plantdiseasedetector.ui.screens.catalog

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import com.example.plantdiseasedetector.ui.screens.classify.PredictionDataState
import com.example.plantdiseasedetector.ui.screens.detail.DiseaseDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseVM @Inject constructor (
    private val repository: DiseaseRepository
) : ViewModel() {


    var queryState = MutableStateFlow<String>("")
    var filterState = MutableStateFlow<Boolean?>(null)

    init {
        updateDiseaseList()
    }

    fun setQuery(query: String) {
        queryState.value = query
    }

    fun setFilter(filter: Boolean?) {
        filterState.value = filter
    }

    private val _diseaseListState = MutableStateFlow<DiseaseListState>(DiseaseListState.Loading)
    val diseaseListState: StateFlow<DiseaseListState> = _diseaseListState.asStateFlow()

    fun updateDiseaseList() {
        viewModelScope.launch {
            try {
                val diseases = repository
                    .getDiseaseByQueryAndFilter(
                        queryState.value,
                        filterState.value
                    )

                _diseaseListState.value = DiseaseListState.Success(diseases)
            }
            catch (e: Exception) {
                _diseaseListState.value = DiseaseListState.Error(
                    message = "Не удалось загрузить данные: ${e.message}"
                )
            }
        }
    }
}