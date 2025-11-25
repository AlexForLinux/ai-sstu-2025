package com.example.plantdiseasedetector.ui.screens.classify

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.ReportItem
import com.example.plantdiseasedetector.data.repository.ClassifyRepository
import com.example.plantdiseasedetector.data.repository.HistoryRepository
import com.example.plantdiseasedetector.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassifyVM @Inject constructor (
    private val classifyRepository: ClassifyRepository,
    private val historyRepository: HistoryRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    var loadedBitmap = MutableStateFlow<Bitmap?>(null)
        private set

    fun setBitmap(bitmap: Bitmap?){
        loadedBitmap.value = bitmap
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
            loadedBitmap.value?.let {
                try {
                    val modelPrediction = classifyRepository.predict(it)
                    _predictionsState.value = PredictionDataState.Success(modelPrediction)

                    val items = modelPrediction.confidences.map { precision ->
                        ReportItem(
                            diseaseClassName = precision.className,
                            confidence = precision.confidence
                        )
                    }

                    val imagePath = imageRepository.saveBitmap(it)
                    historyRepository.createReport(imagePath, items)
                }
                catch (e: Exception){
                    _predictionsState.value = PredictionDataState.Error(
                        "Не удалось обработать изображение"
                    )
                }
            }
        }
    }
}

