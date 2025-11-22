package com.example.plantdiseasedetector.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.datasource.local.ai.PlantDiseaseAI
import com.example.plantdiseasedetector.data.model.DiseasePrecision
import com.example.plantdiseasedetector.data.model.ExpandDiseasePrecision
import com.example.plantdiseasedetector.data.model.ModelPrediction
import com.example.plantdiseasedetector.data.model.PrecisionLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ClassifyRepository {
    fun predict(bitmap: Bitmap?): Flow<ModelPrediction>
}

class ClassifyRepositoryImpl @Inject constructor (
    private val plantDiseaseAI: PlantDiseaseAI,
    private val diseaseDao: DiseaseDao
) : ClassifyRepository {

    override fun predict(bitmap: Bitmap?): Flow<ModelPrediction>  {

        if (bitmap == null){
            throw IllegalArgumentException("Получено пустое изображение")
        }
        else {
            val diseasePrecisions = plantDiseaseAI.classifyByBitmap(bitmap)
            val top1Answer = diseasePrecisions[0]

            var topAnswers : List<DiseasePrecision>?;
            var precisionLevel : PrecisionLevel?;

            if (top1Answer.precision >= 0.80f){
                topAnswers = diseasePrecisions.take(1)
                precisionLevel = PrecisionLevel.HIGH
            }
            else if (top1Answer.precision >= 0.50f) {
                topAnswers = diseasePrecisions.take(2)
                precisionLevel = PrecisionLevel.MEDIUM
            }
            else {
                topAnswers = diseasePrecisions.take(3)
                precisionLevel = PrecisionLevel.LOW
            }

            return diseaseDao.getDiseaseByIds(
                topAnswers.map {it.diseaseId}
            ).map { diseases ->

                val expandDiseasePrecisions =
                topAnswers.map { precision ->
                    val disease = diseases.find { disease ->  disease.id == precision.diseaseId }

                    if (disease == null){
                        return@map ExpandDiseasePrecision(precision, "Здоровое растение")
                    }
                    else {
                        return@map ExpandDiseasePrecision(precision, disease.name)
                    }
                }

                return@map ModelPrediction(
                    precisionLevel,
                    expandDiseasePrecisions
                )
            }
        }
    }
}