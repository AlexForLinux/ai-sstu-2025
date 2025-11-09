package com.example.plantdiseasedetector.data.repository

import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.flow.Flow

interface DiseaseRepository {
    fun getDiseases(): Flow<List<Disease>>
    fun getDiseaseById(id: Int?): Flow<Disease?>
}

class DiseaseRepositoryImpl(
    private val diseaseDao: DiseaseDao
) : DiseaseRepository {
    override fun getDiseases(): Flow<List<Disease>> {
        return diseaseDao.getDiseases()
    }

    override fun getDiseaseById(id: Int?): Flow<Disease?> {
        return diseaseDao.getDiseaseById(id)
    }
}