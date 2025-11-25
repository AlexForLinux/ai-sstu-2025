package com.example.plantdiseasedetector.ui.screens.catalog

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
class DiseaseVM @Inject constructor (
    private val repository: DiseaseRepository
) : ViewModel() {

    var queryState = MutableStateFlow<String>("")
        private set
    var filterState = MutableStateFlow<Boolean?>(null)
        private set

    private val _diseaseListState =
        MutableStateFlow<DiseaseListState>(DiseaseListState.Loading)
    val diseaseListState: StateFlow<DiseaseListState> = _diseaseListState.asStateFlow()

    init {
        updateDiseaseList()
    }

    fun setQuery(query: String) {
        queryState.value = query
    }

    fun setFilter(filter: Boolean?) {
        filterState.value = filter
    }

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
                    message = "Не удалось загрузить данные"
                )
            }
        }
    }
}