package com.example.plantdiseasedetector.data.repository

import com.example.plantdiseasedetector.data.dao.DiseaseDao
import com.example.plantdiseasedetector.data.model.Disease
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ClassifyRepository {}

class ClassifyRepositoryImpl @Inject constructor () : ClassifyRepository {}