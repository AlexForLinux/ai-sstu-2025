package com.example.plantdiseasedetector.ui.functions

import androidx.compose.ui.graphics.Color

fun generateGreenColors(count: Int): List<Color> {
    require(count > 0) { "Count must be positive" }

    return List(count) { index ->
        val hue = 80f + (index * 80f / count)
        val saturation = 0.5f + (index % 3) * 0.25f
        val lightness = 0.5f + (index % 2) * 0.1f

        Color.hsl(hue, saturation, lightness)
    }
}
