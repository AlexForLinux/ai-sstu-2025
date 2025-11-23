package com.example.plantdiseasedetector.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.plantdiseasedetector.data.model.ReportItem
import com.example.plantdiseasedetector.data.model.ReportWithDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


interface ImageRepository {
    suspend fun saveBitmap(bitmap: Bitmap, fileName: String): String?
    suspend fun loadBitmap(filePath: String): Bitmap?
    suspend fun deleteImage(filePath: String): Boolean
    fun generateFileName(): String
}

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageRepository {

    companion object {
        private const val IMAGE_QUALITY = 90
        private const val IMAGE_DIRECTORY = "plant_disease_images"
    }

    // Сохранение Bitmap и возвращение пути
    override suspend fun saveBitmap(bitmap: Bitmap, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val imagesDir = File(context.filesDir, IMAGE_DIRECTORY)
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs()
                }

                val imageFile = File(imagesDir, "$fileName.jpg")
                val outputStream = FileOutputStream(imageFile)

                bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, outputStream)
                outputStream.flush()
                outputStream.close()

                imageFile.absolutePath
            } catch (e: Exception) {
                Log.e("ImageRepository", "Error saving bitmap: ${e.message}")
                null
            }
        }
    }

    // Загрузка Bitmap по пути
    override suspend fun loadBitmap(filePath: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                BitmapFactory.decodeFile(filePath)
            } catch (e: Exception) {
                Log.e("ImageRepository", "Error loading bitmap: ${e.message}")
                null
            }
        }
    }

    // Удаление изображения
    override suspend fun deleteImage(filePath: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                File(filePath).delete()
            } catch (e: Exception) {
                Log.e("ImageRepository", "Error deleting image: ${e.message}")
                false
            }
        }
    }

    // Генерация уникального имени файла
    override fun generateFileName(): String {
        return "image_${System.currentTimeMillis()}"
    }
}