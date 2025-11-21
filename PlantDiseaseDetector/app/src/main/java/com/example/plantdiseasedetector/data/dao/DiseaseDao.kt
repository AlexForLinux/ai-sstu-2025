package com.example.plantdiseasedetector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

    @Query("UPDATE diseases SET marked = :isMarked where id = :id")
    suspend fun updateMark(id: String, isMarked: Boolean)

    @Insert
    suspend fun insertDisease(disease: Disease)

    /* Excessive now */
//    @Query("SELECT * FROM diseases WHERE description LIKE '%' || :query || '%'")
//    fun getDiseasesByQuery(query: String = "") : Flow<List<Disease>>
//
//    @Query("SELECT * FROM diseases WHERE description LIKE '%' || :query || '%' AND marked = :marked")
//    fun getDiseasesByQueryAndMark(query: String = "", marked: Boolean) : Flow<List<Disease>>
//
//    @Query("SELECT * FROM diseases WHERE marked = :marked")
//    fun getDiseasesByMark(marked: Boolean) : Flow<List<Disease>>
}