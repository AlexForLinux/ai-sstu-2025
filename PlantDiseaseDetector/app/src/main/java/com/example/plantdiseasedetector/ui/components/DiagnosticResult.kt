package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.data.model.ConfidenceLevel
import com.example.plantdiseasedetector.data.model.ExpandDiseaseConfidence

@Composable
fun DiagnosticResult(
    confidences: List<ExpandDiseaseConfidence>,
    confidenceLevel:  ConfidenceLevel,
    colors: List<Color>,
    onDiseaseClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            "Результаты диагностики",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth(),
        )

        Spacer(Modifier.height(8.dp))

        Card (
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .fillMaxWidth()
            )
            {
                val diseases = confidences
                    .mapNotNull { confidence ->
                        confidence.diseaseId?.let { diseaseId ->
                            diseaseId to confidence.diseaseName
                        }
                    }
                    .toMap()

                when (confidenceLevel) {

                    ConfidenceLevel.HIGH -> {
                        TextWithLinks(
                            "Вероятный диагноз: ${confidences[0].diseaseName}",
                            diseases,
                            onDiseaseClick = { id -> onDiseaseClick(id) },
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Justify
                        )
                    }

                    ConfidenceLevel.MEDIUM -> {
                        TextWithLinks(
                            "Вероятный диагноз: ${confidences[0].diseaseName}\n" +
                                    "Однако есть основания для другого диагноза: ${confidences[1].diseaseName}",
                            diseases,
                            onDiseaseClick = { id -> onDiseaseClick(id) },
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Justify
                        )
                    }

                    ConfidenceLevel.LOW -> {
                        Text(
                            "Не удалось однозначно определить диагноз. Попробуйте сделать фотографию при лучшем освещении или с другого ракурса.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card (
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                ConfidencePieChart(
                    confidences = confidences.map { it.confidence },
                    defaultColors = colors,
                    modifier = Modifier
                        .size(128.dp),
                )

                Spacer(Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    confidences.forEachIndexed { idx, precision ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            colors[idx]
                                        )
                                ) {
                                    Text(
                                        (idx + 1).toString(),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(precision.diseaseName)
                            }
                        }
                    }
                }
            }
        }
    }
}