package com.example.plantdiseasedetector.data.repository

import android.graphics.Bitmap
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.datasource.local.ai.PlantDiseaseAI
import com.example.plantdiseasedetector.data.model.ExpandDiseaseConfidence
import com.example.plantdiseasedetector.data.model.ModelPrediction
import com.example.plantdiseasedetector.data.model.ConfidenceLevel
import com.example.plantdiseasedetector.data.model.DiseaseConfidence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ClassifyRepository {
    suspend fun predict(bitmap: Bitmap?): ModelPrediction
}

class ClassifyRepositoryImpl @Inject constructor (
    private val plantDiseaseAI: PlantDiseaseAI,
    private val diseaseDao: DiseaseDao
) : ClassifyRepository {

    override suspend fun predict(bitmap: Bitmap?): ModelPrediction {

        if (bitmap == null){
            throw IllegalArgumentException("Получено пустое изображение")
        }
        else {
            val diseaseConfidences = plantDiseaseAI.classifyByBitmap(bitmap)
            val top1Answer = diseaseConfidences[0]

            var topAnswers : List<DiseaseConfidence>
            var confidenceLevel : ConfidenceLevel

            if (top1Answer.confidence >= 0.80f){
                topAnswers = diseaseConfidences.take(1)
                confidenceLevel = ConfidenceLevel.HIGH
            }
            else if (top1Answer.confidence >= 0.50f) {
                topAnswers = diseaseConfidences.take(2)
                confidenceLevel = ConfidenceLevel.MEDIUM
            }
            else {
                topAnswers = diseaseConfidences.take(3)
                confidenceLevel = ConfidenceLevel.LOW
            }

            val diseases = diseaseDao.getDiseaseByClassNames(
                topAnswers.map {it.className}
            )

            val expandDiseaseConfidences =
            topAnswers.map { confidence ->
                val disease = diseases.find { disease ->
                    disease.className == confidence.className
                }

                if (disease == null){
                    return@map ExpandDiseaseConfidence(
                        confidence,
                        null,
                        "Здоровое растение"
                    )
                }

                return@map ExpandDiseaseConfidence(
                    confidence,
                    disease.id,
                    disease.name
                )
            }

            return ModelPrediction(
                confidenceLevel,
                expandDiseaseConfidences
            )
        }
    }
}