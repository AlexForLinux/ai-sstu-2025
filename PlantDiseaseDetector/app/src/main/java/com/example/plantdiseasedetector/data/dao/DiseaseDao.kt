package com.example.plantdiseasedetector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDao {
    @Query("SELECT * FROM diseases")
    fun getDiseases() : Flow<List<Disease>>

    @Insert
    suspend fun insertDisease(disease: Disease)
}