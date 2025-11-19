package com.example.plantdiseasedetector.data.datasource.local.ai

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.get
import androidx.core.graphics.scale
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.Tensor
import org.pytorch.torchvision.TensorImageUtils
import java.io.File

class PlantDiseaseAIImpl (
    modelFile: String
) : PlantDiseaseAI {
    val width = 224
    val height = 224

    val mean = floatArrayOf(0.4230f, 0.5228f, 0.3154f)
    val std = floatArrayOf(0.1991f, 0.1988f, 0.1910f)

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

    override fun classifyByBitmap(bitmap: Bitmap) : FloatArray {
        val inputTensor = bitmapToFloatTensor(bitmap)
        val outputTensor = module.forward(IValue.from(inputTensor)).toTensor()

        val scores = outputTensor.dataAsFloatArray
        return softmax(scores)
    }

    override fun getClassNames() : Array<String> {
        return arrayOf("Healthy", "Powdery", "Rust", "Slug", "Spot")
    }
}