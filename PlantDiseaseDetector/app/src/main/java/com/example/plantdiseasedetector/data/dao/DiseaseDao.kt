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

    @Query("SELECT * FROM diseases where id = :id")
    fun getDiseaseById(id: String?) : Flow<Disease>

    @Query("SELECT * FROM diseases where id in (:ids)")
    fun getDiseaseByIds(ids: List<String>?) : Flow<List<Disease>>

    @Insert
    suspend fun insertDisease(disease: Disease)

    @Query("SELECT * FROM diseases")
    suspend fun getAllDiseasesOnce(): List<Disease>
}