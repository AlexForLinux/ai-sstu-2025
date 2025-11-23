package com.example.plantdiseasedetector.data.datasource.local.ai

import android.graphics.Bitmap
import com.example.plantdiseasedetector.data.model.DiseaseConfidence

interface PlantDiseaseAI {
    fun classifyByBitmap(bitmap: Bitmap) : List<DiseaseConfidence>
}

