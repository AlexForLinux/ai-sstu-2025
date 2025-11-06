package com.example.plantdiseasedetector.data.repository

import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.datasource.local.db.LocalDataBase
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.flow.Flow

interface DiseaseRepository {
    fun getDiseases(): Flow<List<Disease>>
}

class DiseaseRepositoryImpl(
    private val diseaseDao: DiseaseDao
) : DiseaseRepository {
    override fun getDiseases(): Flow<List<Disease>> {
        return diseaseDao.getDiseases()
    }
}