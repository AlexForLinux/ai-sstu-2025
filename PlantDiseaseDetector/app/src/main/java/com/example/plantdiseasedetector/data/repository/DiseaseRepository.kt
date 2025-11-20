package com.example.plantdiseasedetector.data.repository

import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DiseaseRepository {
    fun getDiseases(): Flow<List<Disease>>
    fun getDiseaseById(id: String?): Flow<Disease?>
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
}