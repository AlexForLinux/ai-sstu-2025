package com.example.plantdiseasedetector.di

import com.example.plantdiseasedetector.data.repository.ClassifyRepository
import com.example.plantdiseasedetector.data.repository.ClassifyRepositoryImpl
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import com.example.plantdiseasedetector.data.repository.DiseaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDiseaseRepository(repositoryImpl: DiseaseRepositoryImpl): DiseaseRepository

    @Binds
    @Singleton
    abstract fun bindClassifyRepository(repositoryImpl: ClassifyRepositoryImpl): ClassifyRepository
}