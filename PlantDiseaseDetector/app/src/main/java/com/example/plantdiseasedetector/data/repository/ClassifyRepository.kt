package com.example.plantdiseasedetector.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.datasource.local.ai.PlantDiseaseAI
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.Prediction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface ClassifyRepository {
    fun predict(bitmap: Bitmap?): Flow<List<Prediction>>
}

class ClassifyRepositoryImpl @Inject constructor (
    private val plantDiseaseAI: PlantDiseaseAI,
) : ClassifyRepository {

    override fun predict(bitmap: Bitmap?): Flow<List<Prediction>> = flow {

        if (bitmap == null){
            throw IllegalArgumentException("Получено пустое изображение")
        }
        else {
            val classNames = plantDiseaseAI.getClassNames()
            val scores = plantDiseaseAI.classifyByBitmap(bitmap)

            val result = classNames.indices.map { i ->
                Prediction(classNames[i], scores[i] * 100)
            }

            val sorted = result.sortedByDescending { it.precision }
            emit(sorted.take(3))
        }
    }.flowOn(Dispatchers.Default)
}