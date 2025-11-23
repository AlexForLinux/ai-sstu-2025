package com.example.plantdiseasedetector.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class DiseaseWithAdvice(
    @Embedded
    val disease: Disease,

    @Relation(
        parentColumn = "id",
        entityColumn = "diseaseId"
    )
    val advice: List<Advice>
)