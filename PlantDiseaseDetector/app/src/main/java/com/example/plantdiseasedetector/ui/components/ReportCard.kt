package com.example.plantdiseasedetector.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantdiseasedetector.data.model.ReportWithDetails
import com.example.plantdiseasedetector.ui.functions.formatDateTime
import com.example.plantdiseasedetector.ui.functions.generateGreenColors

@Composable
fun ReportCard(
    report: ReportWithDetails,
    image: Bitmap?,
    onDelete: (Long) -> Unit,
    colors: List<Color> = generateGreenColors(report.detailedItems.size),
    modifier: Modifier = Modifier
) {

    Box (
        modifier = modifier
    ) {

        Card (
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Column (
                modifier = Modifier.padding(8.dp)
            ) {

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Отчет от " + formatDateTime(report.report.createdAt),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                Spacer(Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = image,
                            contentDescription = "Фото",
                            modifier = Modifier.size(112.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        report.detailedItems.forEachIndexed { idx, detailedItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .size(28.dp)
//                                            .clip(RoundedCornerShape(16.dp))
//                                            .background(
//                                                colors[idx]
//                                            ),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        Text(
//                                            (idx + 1).toString(),
//                                            color = MaterialTheme.colorScheme.onPrimary
//                                        )
//                                    }

                                    ConfidencePieChart(
                                        floatArrayOf(detailedItem.item.confidence).asList(),
                                        modifier = Modifier.size(28.dp),
                                        defaultColors = colors,
                                        grayColor = Color.LightGray,
                                        showLabels = false
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        detailedItem.diseaseName ?: "Здоровое растение"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-4).dp, y = (4).dp)
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    RoundedCornerShape(8.dp)
                )
                .clickable(onClick = {
                    onDelete(report.report.id)
                })

        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Сделать фото",
                tint = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.size(24.dp).align(Alignment.Center)
            )
        }
    }
}