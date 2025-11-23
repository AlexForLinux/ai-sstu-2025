package com.example.plantdiseasedetector.data.model

data class ExpandDiseaseConfidence(
    private val diseaseConfidence: DiseaseConfidence,
    val diseaseId: Long?,
    val diseaseName: String
) {
    val className: String by diseaseConfidence::className
    val confidence: Float by diseaseConfidence::confidence
}