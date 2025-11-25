package com.example.plantdiseasedetector.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

interface ImageRepository {
    suspend fun saveBitmap(bitmap: Bitmap): String
    suspend fun loadBitmap(filePath: String): Bitmap?
    suspend fun deleteImage(filePath: String)
}

class ImageRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ImageRepository {

    companion object {
        private const val IMAGE_QUALITY = 80
        private const val IMAGE_DIRECTORY = "plant_disease_images"
    }

    override suspend fun saveBitmap(bitmap: Bitmap): String {
        return withContext(Dispatchers.IO) {
            val imagesDir = File(context.filesDir, IMAGE_DIRECTORY)
            if (!imagesDir.exists()) {
                imagesDir.mkdirs()
            }

            val fileName = generateFileName()
            val imageFile = File(imagesDir, "$fileName.jpg")
            val outputStream = FileOutputStream(imageFile)

            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, outputStream)
            outputStream.flush()
            outputStream.close()

            imageFile.absolutePath
        }
    }

    override suspend fun loadBitmap(filePath: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                BitmapFactory.decodeFile(filePath)
            }
            catch (e: Exception){
                null
            }
        }
    }

    override suspend fun deleteImage(filePath: String) {
        return withContext(Dispatchers.IO) {
            File(filePath).delete()
        }
    }

    private fun generateFileName(): String {
        return "image_${System.currentTimeMillis()}"
    }
}