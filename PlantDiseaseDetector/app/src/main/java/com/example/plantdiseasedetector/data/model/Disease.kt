package com.example.plantdiseasedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("diseases")
data class Disease (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val imageId: Int,
    val marked: Boolean,
)