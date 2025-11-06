package com.example.plantdiseasedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("diseases")
data class Disease (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val shortDesc: String
)