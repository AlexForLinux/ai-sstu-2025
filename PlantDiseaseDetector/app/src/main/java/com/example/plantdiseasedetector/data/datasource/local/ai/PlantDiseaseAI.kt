package com.example.plantdiseasedetector.data.datasource.local.ai

import android.content.Context
import android.graphics.Bitmap
import org.pytorch.Tensor
import java.io.File
import androidx.core.graphics.scale
import androidx.core.graphics.get
import org.pytorch.IValue
import org.pytorch.Module

interface PlantDiseaseAI {
    fun classifyByBitmap(bitmap: Bitmap) : FloatArray
    fun getClassNames() : Array<String>
}

