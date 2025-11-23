package com.example.plantdiseasedetector.data.model

data class ModelPrediction(
    val confidenceLevel: ConfidenceLevel,
    val confidences: List<ExpandDiseaseConfidence>
)
