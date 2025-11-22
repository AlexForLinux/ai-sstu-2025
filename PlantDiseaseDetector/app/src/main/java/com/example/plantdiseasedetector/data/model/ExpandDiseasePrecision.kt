package com.example.plantdiseasedetector.data.model

data class ExpandDiseasePrecision(
    private val diseasePrecision: DiseasePrecision,
    val name: String
) {
    val diseaseId: String by diseasePrecision::diseaseId
    val precision: Float by diseasePrecision::precision
}