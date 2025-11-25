package com.example.plantdiseasedetector.data.repository

import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.DiseaseWithAdvice
import javax.inject.Inject

interface DiseaseRepository {
    suspend fun getDiseases(): List<Disease>
    suspend fun getDiseaseById(id: Long): Disease
    suspend fun getDiseaseWithAdviceById(id: Long): DiseaseWithAdvice

    suspend fun getDiseaseByQueryAndFilter(query: String, filter: Boolean?): List<Disease>
    suspend fun updateDiseaseMark(id: Long, isMarked: Boolean)
}

class DiseaseRepositoryImpl @Inject constructor (
    private val diseaseDao: DiseaseDao
) : DiseaseRepository {

    override suspend fun getDiseases(): List<Disease> {
        return diseaseDao.getDiseases()
    }

    override suspend fun getDiseaseById(id: Long): Disease {
        return diseaseDao.getDiseaseById(id)
    }

    override suspend fun getDiseaseWithAdviceById(id: Long): DiseaseWithAdvice {
        return diseaseDao.getDiseaseWithAdviceById(id)
    }

    override suspend fun getDiseaseByQueryAndFilter(query: String, filter: Boolean?): List<Disease> {

        val diseases = diseaseDao.getDiseases()

        return diseases.filter { disease ->
            (filter == null || disease.marked == filter)
            && disease.description.lowercase().contains(query.lowercase())
        }
    }

    override suspend fun updateDiseaseMark(id: Long, isMarked: Boolean) {
        diseaseDao.updateMark(id, isMarked)
    }
}