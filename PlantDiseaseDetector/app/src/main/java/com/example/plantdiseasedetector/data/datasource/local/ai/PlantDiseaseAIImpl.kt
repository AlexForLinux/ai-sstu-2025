package com.example.plantdiseasedetector.data.datasource.local.ai

import android.graphics.Bitmap
import androidx.core.graphics.scale
import com.example.plantdiseasedetector.data.model.DiseaseConfidence
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils

class PlantDiseaseAIImpl (
    modelFile: String,
    val width: Int = 224,
    val height: Int = 224,
    val mean: FloatArray = floatArrayOf(0.4230f, 0.5228f, 0.3154f),
    val std: FloatArray = floatArrayOf(0.1991f, 0.1988f, 0.1910f),
    val classes: Array<String> = arrayOf("healthy", "powdery", "rust", "slug", "spot")
) : PlantDiseaseAI {
    val module: Module = Module.load(modelFile)

    private fun bitmapToFloatTensor(bitmap: Bitmap): Tensor {

        val resized = bitmap.scale(width, height)

        return TensorImageUtils.bitmapToFloat32Tensor(
            resized,
            mean,
            std
        )
    }

    private fun softmax(input: FloatArray): FloatArray {
        val max = input.max()
        val exp = input.map { kotlin.math.exp(it - max) }
        val sum = exp.sum()
        return exp.map { it / sum }.toFloatArray()
    }

    override fun classifyByBitmap(bitmap: Bitmap) : List<DiseaseConfidence> {
        val inputTensor = bitmapToFloatTensor(bitmap)
        val outputTensor = module.forward(IValue.from(inputTensor)).toTensor()

        val scores = softmax(outputTensor.dataAsFloatArray)
        val predictionList = scores.mapIndexed { idx, score ->
            DiseaseConfidence(classes[idx], score)
        }

        return predictionList.sortedBy { - it.confidence }
    }
}