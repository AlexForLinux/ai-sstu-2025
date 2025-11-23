package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import com.example.plantdiseasedetector.ui.functions.generateGreenColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ConfidencePieChart(
    confidences: List<Float>,
    showLabels: Boolean = true,
    modifier: Modifier = Modifier,
    defaultColors: List<Color> = generateGreenColors(confidences.size),
    grayColor: Color = Color.Gray.copy(alpha = 0.5f)
) {
    require(confidences.isNotEmpty()) { "Precisions list cannot be empty" }
    require(confidences.all { it >= 0 }) { "All precision values must be non-negative" }
    require(confidences.sum() <= 1.0f) { "All precision values must be non-negative" }

    Canvas (
        modifier = modifier
    ) {
        val diameter = size.minDimension
        val strokeWidth = diameter / 15
        val radius = diameter / 2 - strokeWidth / 2
        val center = Offset(size.width / 2, size.height / 2)

        drawCircle(
            color = grayColor,
            radius = radius,
            center = center
        )

        var startAngle = -90f

        confidences.forEachIndexed { index, confidence ->

            val sweepAngle = confidence * 360f

            drawArc(
                color = defaultColors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(
                    center.x - radius,
                    center.y - radius
                ),
                size = Size(radius * 2, radius * 2)
            )

            if (confidence >= 0.125f && showLabels) {
                val labelAngle = startAngle + sweepAngle / 2
                val labelRadius = radius * 0.6f
                val labelPosition = Offset(
                    center.x + labelRadius * cos(Math.toRadians(labelAngle.toDouble())).toFloat(),
                    center.y + labelRadius * sin(Math.toRadians(labelAngle.toDouble())).toFloat()
                )

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${"%.1f".format(confidence * 100)}%",
                        labelPosition.x,
                        labelPosition.y,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = diameter / 10
                            textAlign = android.graphics.Paint.Align.CENTER
                            isFakeBoldText = true
                        }
                    )
                }
            }

            startAngle += sweepAngle
        }
    }
}