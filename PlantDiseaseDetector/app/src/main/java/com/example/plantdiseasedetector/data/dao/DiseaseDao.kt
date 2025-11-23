package com.example.plantdiseasedetector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.plantdiseasedetector.data.model.Advice
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.DiseaseWithAdvice
import com.example.plantdiseasedetector.data.model.Report
import com.example.plantdiseasedetector.data.model.ReportItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDao {
    @Query("SELECT * FROM diseases")
    suspend fun getDiseases() : List<Disease>

    @Query("SELECT * FROM diseases where id = :id")
    suspend fun getDiseaseById(id: Long) : Disease

    @Query("SELECT * FROM diseases where className in (:classNames)")
    suspend fun getDiseaseByClassNames(classNames: List<String>) : List<Disease>

    @Query("UPDATE diseases SET marked = :isMarked where id = :id")
    suspend fun updateMark(id: Long, isMarked: Boolean)

    @Insert
    suspend fun insertDisease(disease: Disease) : Long

    @Insert

    /* Excessive now */
//    @Query("SELECT * FROM diseases WHERE description LIKE '%' || :query || '%'")
//    suspend fun getDiseasesByQuery(query: String = "") : List<Disease>
//
//    @Query("SELECT * FROM diseases WHERE description LIKE '%' || :query || '%' AND marked = :marked")
//    suspend fun getDiseasesByQueryAndMark(query: String = "", marked: Boolean) : List<Disease>
//
//    @Query("SELECT * FROM diseases WHERE marked = :marked")
//    suspend fun getDiseasesByMark(marked: Boolean) : List<Disease>
}