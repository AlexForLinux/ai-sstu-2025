package com.example.plantdiseasedetector.ui.screens.classify

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.Prediction
import com.example.plantdiseasedetector.data.repository.ClassifyRepository
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import com.example.plantdiseasedetector.ui.screens.detail.DiseaseDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassifyVM @Inject constructor (
    private val repository: ClassifyRepository
) : ViewModel() {

    var loadedBitmap by mutableStateOf<Bitmap?>(null)
        private set

    fun setBitmap(bitmap: Bitmap?){
        loadedBitmap = bitmap
        setEmptyData()
    }
    private val _predictionsState = MutableStateFlow<PredictionDataState>(PredictionDataState.EmptyData)
    val predictionsState: StateFlow<PredictionDataState> = _predictionsState.asStateFlow()

    private fun setEmptyData() {
        _predictionsState.value = PredictionDataState.EmptyData
    }
    fun predict() {
        _predictionsState.value = PredictionDataState.Loading
        viewModelScope.launch {
            repository.predict(loadedBitmap)
            .catch { exception ->
                _predictionsState.value = PredictionDataState.Error(
                    "Не удалось обработать изображение: ${exception.message}"
                )
            }
            .collect { predictions ->
                if (predictions.isNotEmpty()) {
                    _predictionsState.value = PredictionDataState.Success(predictions)
                } else {
                    _predictionsState.value = PredictionDataState.Error("Не удалось обработать изображение")
                }
            }
        }
    }
}

