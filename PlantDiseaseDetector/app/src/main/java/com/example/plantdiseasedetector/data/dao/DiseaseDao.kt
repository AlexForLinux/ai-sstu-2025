package com.example.plantdiseasedetector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.plantdiseasedetector.data.model.Advice
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.DiseaseWithAdvice

@Dao
interface DiseaseDao {
    @Query("SELECT * FROM diseases")
    suspend fun getDiseases() : List<Disease>

    @Query("SELECT * FROM diseases where id = :id")
    suspend fun getDiseaseById(id: Long) : Disease

    @Query("SELECT * FROM diseases where id = :id")
    suspend fun getDiseaseWithAdviceById(id: Long) : DiseaseWithAdvice

    @Query("SELECT * FROM diseases where className in (:classNames)")
    suspend fun getDiseaseByClassNames(classNames: List<String>) : List<Disease>

    @Query("UPDATE diseases SET marked = :isMarked where id = :id")
    suspend fun updateMark(id: Long, isMarked: Boolean)

    @Insert
    suspend fun insertDisease(disease: Disease) : Long

    @Insert
    suspend fun insertAdvice(advice: List<Advice>)

    @Transaction
    suspend fun insertDiseaseWithAdvice(disease: Disease, advice: List<Advice>): Long {
        val diseaseId = insertDisease(disease)

        val adviceWithDiseaseId = advice.map { it.copy(diseaseId = diseaseId) }
        insertAdvice(adviceWithDiseaseId)

        return diseaseId
    }
}