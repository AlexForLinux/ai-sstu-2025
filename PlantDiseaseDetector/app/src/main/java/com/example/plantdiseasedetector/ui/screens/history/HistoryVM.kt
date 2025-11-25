package com.example.plantdiseasedetector.ui.screens.history

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
import com.example.plantdiseasedetector.ui.screens.classify.PredictionDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor (
    private val historyRepository: HistoryRepository,
    private val imageRepository: ImageRepository,
) : ViewModel() {

    private val _historyState = MutableStateFlow<HistoryDataState>(HistoryDataState.Loading)
    val historyState: StateFlow<HistoryDataState> = _historyState.asStateFlow()

    private val _imagesCache = MutableStateFlow<Map<Long, Bitmap?>>(emptyMap())
    val imagesState: StateFlow<Map<Long, Bitmap?>> = _imagesCache.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _historyState.value = HistoryDataState.Loading
            try {
                val reports = historyRepository.getAllReports()
                _historyState.value = HistoryDataState.Success(reports)

                val res = reports.associate { report ->
                    report.report.id to imageRepository.loadBitmap(report.report.imagePath)
                }

                _imagesCache.value = res

            } catch (e: Exception) {
                _historyState.value =
                    HistoryDataState.Error("Не удалось загузить историю")
            }
        }
    }

    fun deleteReport(reportId: Long) {
        viewModelScope.launch {
            _historyState.value.let { state ->
                if (state is HistoryDataState.Success){
                    val item = state.reports.find { (report, _) -> report.id == reportId }

                    if (item != null) {
                        imageRepository.deleteImage(item.report.imagePath).let {
                            historyRepository.deleteReport(reportId).let {
                                _historyState.value = HistoryDataState.Success(
                                    state.reports.filter {
                                        it.report.id != reportId
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}