package com.example.plantdiseasedetector.ui.screens.classify

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.repository.ClassifyRepository
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ClassifyVM @Inject constructor (
    private val repository: ClassifyRepository
) : ViewModel() {

    var selectedImageBitmap by mutableStateOf<Bitmap?>(null)
        private set

    fun setBitmap(bitmap: Bitmap?){
        selectedImageBitmap = bitmap
    }
}

