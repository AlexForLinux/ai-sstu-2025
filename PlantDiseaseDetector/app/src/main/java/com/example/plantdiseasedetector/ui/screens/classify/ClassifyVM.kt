package com.example.plantdiseasedetector.ui.screens.classify

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.DiseasePrecision
import com.example.plantdiseasedetector.data.model.ReportItem
import com.example.plantdiseasedetector.data.repository.ClassifyRepository
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import com.example.plantdiseasedetector.data.repository.HistoryRepository
import com.example.plantdiseasedetector.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ClassifyVM @Inject constructor (
    private val classifyRepository: ClassifyRepository,
    private val historyRepository: HistoryRepository,
    private val imageRepository: ImageRepository
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
            loadedBitmap?.let {
                classifyRepository.predict(it)
                    .catch { exception ->
                        _predictionsState.value = PredictionDataState.Error(
                            "Не удалось обработать изображение: ${exception.message}"
                        )
                    }
                    .collect { modelPrediction ->
        //                TODO: should they granted to complete both?
                        _predictionsState.value = PredictionDataState.Success(modelPrediction)

                        val items = modelPrediction.precisions.map { precision ->
                            ReportItem(
                                diseaseId = if (precision.diseaseId != "healthy") precision.diseaseId else null,
                                precision = precision.precision
                            )
                        }

                        val imageFileName = imageRepository.generateFileName()
                        val imagePath = imageRepository.saveBitmap(loadedBitmap!!, imageFileName)
                        if (imagePath != null)
                            historyRepository.createReport(imagePath, items)
                    }
            }
        }
    }
}

