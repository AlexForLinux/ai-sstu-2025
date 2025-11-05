package com.example.plantdiseasedetector.data.repository

import com.example.plantdiseasedetector.data.datasource.local.db.TestDataBase
import com.example.plantdiseasedetector.data.model.DiseasePreviewModel

interface DiseasePreviewRepository {
    fun getDiseasePreviewList(): List<DiseasePreviewModel>
}

class DiseasePreviewRepositoryImpl(
    private val localDataSource: TestDataBase
) : DiseasePreviewRepository {
    override fun getDiseasePreviewList(): List<DiseasePreviewModel> {
        return localDataSource.getDiseasePreviewList()
    }
}