package com.example.plantdiseasedetector.data.datasource.local.ai

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.graphics.Bitmap
import androidx.core.graphics.scale
import com.example.plantdiseasedetector.data.model.DiseaseConfidence
import java.nio.FloatBuffer

class PlantDiseaseAIOnnxImpl (
    val modelFile: String,
    val size: Int = 224,
    val mean: FloatArray = floatArrayOf(0.4230f, 0.5228f, 0.3154f),
    val std: FloatArray = floatArrayOf(0.1991f, 0.1988f, 0.1910f),
    val classes: Array<String> = arrayOf("healthy", "powdery", "rust", "slug", "spot")
) : PlantDiseaseAI {

    private lateinit var session: OrtSession
    private lateinit var env: OrtEnvironment

    init {
        loadModel()
    }

    private fun loadModel() {
        env = OrtEnvironment.getEnvironment()
        val sessionOptions = OrtSession.SessionOptions()
        session = env.createSession(modelFile, sessionOptions)
    }

    private fun prepareInput(bitmap: Bitmap): OnnxTensor {
        val resized = bitmap.scale(size, size)
        val pixels = IntArray(size * size)
        resized.getPixels(pixels, 0, size, 0, 0, size, size)

        val floatArray = FloatArray(3 * size * size)

        for (i in pixels.indices) {
            val pixel = pixels[i]
            floatArray[i] = (((pixel shr 16) and 0xFF) / 255.0f - mean[0]) / std[0]
            floatArray[i + size*size] = (((pixel shr 8) and 0xFF) / 255.0f - mean[1]) /  std[0]
            floatArray[i + 2*size*size] = ((pixel and 0xFF) / 255.0f - mean[2]) /  std[0]
        }

        return OnnxTensor.createTensor(
            env,
            FloatBuffer.wrap(floatArray),
            longArrayOf(1, 3, size.toLong(), size.toLong())
        )
    }

    private fun softmax(input: FloatArray): FloatArray {
        val max = input.max()
        val exp = input.map { kotlin.math.exp(it - max) }
        val sum = exp.sum()
        return exp.map { it / sum }.toFloatArray()
    }

    override fun classifyByBitmap(bitmap: Bitmap) : List<DiseaseConfidence> {

        val input = prepareInput(bitmap)
        val results = session.run(mapOf("input" to input))
        val output = (results[0].value as Array<FloatArray>)[0]

        val scores = softmax(output)
        val predictionList = scores.mapIndexed { idx, score ->
            DiseaseConfidence(classes[idx], score)
        }

        return predictionList.sortedBy { - it.confidence }
    }
}