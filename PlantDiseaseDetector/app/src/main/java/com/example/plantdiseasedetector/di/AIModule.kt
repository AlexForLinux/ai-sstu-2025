package com.example.plantdiseasedetector.di

import android.content.Context
import com.example.plantdiseasedetector.data.datasource.local.ai.PlantDiseaseAI
import com.example.plantdiseasedetector.data.datasource.local.ai.PlantDiseaseAIImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIModule {

    @Provides
    @Singleton
    fun provideAI(
        @ApplicationContext context: Context,
    ): PlantDiseaseAI {

        val modelName = "eff-net-b1-best-mobile.pt"

        val file = File(context.filesDir, modelName)
        if (!file.exists()) {
            context.assets.open(modelName).use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        return PlantDiseaseAIImpl(
            file.absolutePath
        )
    }
}