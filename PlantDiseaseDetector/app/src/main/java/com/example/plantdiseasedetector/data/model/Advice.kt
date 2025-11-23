package com.example.plantdiseasedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/* 'advice' in plural is also 'advice', not 'advices'!*/
@Entity("advice")
data class Advice(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val diseaseId: Long = 0,
    val title: String,
    val text: String
)