package com.example.plantdiseasedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("report_items")
data class ReportItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val reportId: Long = 0,
    val diseaseId: String?,
    val precision: Float,
)