package com.example.plantdiseasedetector.di

import com.example.plantdiseasedetector.data.repository.ClassifyRepository
import com.example.plantdiseasedetector.data.repository.ClassifyRepositoryImpl
import com.example.plantdiseasedetector.data.repository.DiseaseRepository
import com.example.plantdiseasedetector.data.repository.DiseaseRepositoryImpl
import com.example.plantdiseasedetector.data.repository.HistoryRepository
import com.example.plantdiseasedetector.data.repository.HistoryRepositoryImpl
import com.example.plantdiseasedetector.data.repository.ImageRepository
import com.example.plantdiseasedetector.data.repository.ImageRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(repositoryImpl: HistoryRepositoryImpl): HistoryRepository

    @Binds
    @Singleton
    abstract fun bindImageRepository(repositoryImpl: ImageRepositoryImpl): ImageRepository
}