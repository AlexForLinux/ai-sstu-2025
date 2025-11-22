package com.example.plantdiseasedetector.data.repository

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DiseaseRepository {
    fun getDiseases(): Flow<List<Disease>>
    fun getDiseaseById(id: String?): Flow<Disease?>

    fun getDiseaseByQueryAndFilter(query: String, filter: Boolean?): Flow<List<Disease>>
    suspend fun updateDiseaseMark(id: String, isMarked: Boolean)
}

class DiseaseRepositoryImpl @Inject constructor (
    private val diseaseDao: DiseaseDao
) : DiseaseRepository {
    override fun getDiseases(): Flow<List<Disease>> {
        return diseaseDao.getDiseases()
    }

    override fun getDiseaseById(id: String?): Flow<Disease?> {
        return diseaseDao.getDiseaseById(id)
    }

    override fun getDiseaseByQueryAndFilter(query: String, filter: Boolean?): Flow<List<Disease>> {

        /* I believe it's excessive for 4 items to use db search, so...*/

//        if (filter != null){
//            if (query.isEmpty()) return diseaseDao.getDiseasesByMark(filter)
//            return diseaseDao.getDiseasesByQueryAndMark(query, filter)
//        }
//
//        if (!query.isEmpty()) return diseaseDao.getDiseasesByQuery(query)
//        return diseaseDao.getDiseases()

        return diseaseDao.getDiseases().map {
            diseases ->
            diseases.filter { disease ->

                (filter == null || disease.marked == filter)
                && disease.description.lowercase().contains(query.lowercase())

            }
        }
    }

    override suspend fun updateDiseaseMark(id: String, isMarked: Boolean) {
        diseaseDao.updateMark(id, isMarked)
    }
}