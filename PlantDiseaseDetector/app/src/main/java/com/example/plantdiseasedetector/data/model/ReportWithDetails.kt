package com.example.plantdiseasedetector.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ReportWithDetails(
    @Embedded
    val report: Report,

    @Relation(
        parentColumn = "id",
        entityColumn = "reportId"
    )
    val detailedItems: List<ReportDetailedItem>
)
