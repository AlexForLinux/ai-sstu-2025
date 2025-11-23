package com.example.plantdiseasedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val imagePath: String
)