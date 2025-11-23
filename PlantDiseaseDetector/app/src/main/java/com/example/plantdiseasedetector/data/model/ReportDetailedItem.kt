package com.example.plantdiseasedetector.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ReportDetailedItem(
    @Embedded
    val item: ReportItem,
    val diseaseName: String?
)
