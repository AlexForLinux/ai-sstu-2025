package com.example.plantdiseasedetector.data.model

import androidx.room.Embedded

data class ReportDetailedItem(
    @Embedded
    val item: ReportItem,
    val diseaseName: String?
)
