package com.example.plantdiseasedetector.data.model

data class ModelPrediction(
    val precisionLevel: PrecisionLevel,
    val precisions: List<ExpandDiseasePrecision>
)
