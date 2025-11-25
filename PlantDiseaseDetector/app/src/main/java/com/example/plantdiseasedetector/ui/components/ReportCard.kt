package com.example.plantdiseasedetector.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantdiseasedetector.data.model.ReportWithDetails
import com.example.plantdiseasedetector.ui.functions.formatDateTime
import com.example.plantdiseasedetector.ui.functions.generateGreenColors

@Composable
fun ReportCard(
    modifier: Modifier = Modifier,
    report: ReportWithDetails,
    image: Bitmap?,
    onDelete: (Long) -> Unit,
    colors: List<Color> = generateGreenColors(report.detailedItems.size),
) {

    var deleteWarning by remember { mutableStateOf<Boolean>(false)}

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

            if (deleteWarning) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(142.dp)
                ){
                    Text(
                        "Точно желаете удалить данный отчет?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                    ){
                        Button(
                            onClick = {
                                onDelete(report.report.id)
                                deleteWarning = false
                            },
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.LightGray
                            ),
                            modifier = Modifier
                                .width(96.dp)
                        ) {
                            Text("Да")
                        }

                        Spacer(modifier = Modifier.width(40.dp))

                        Button(
                            onClick = { deleteWarning = false },
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.LightGray
                            ),
                            modifier = Modifier
                                .width(96.dp)
                        ) {
                            Text("Нет")
                        }
                    }
                }
            }
            else {
                Column (
                    modifier = Modifier
                        .padding(8.dp)
                ) {

                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Отчет от " + formatDateTime(report.report.createdAt),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    Spacer(Modifier.height(4.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {


                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            AsyncImage(
                                model = image,
                                contentDescription = "Фото",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(112.dp),
                            )
                        }

                        Spacer(Modifier.width(8.dp))

                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            report.detailedItems.forEach { detailedItem ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        ConfidencePieChart(
                                            confidences =
                                                floatArrayOf(detailedItem.item.confidence).asList(),
                                            defaultColors = colors,
                                            grayColor = Color.LightGray,
                                            showLabels = false,
                                            modifier = Modifier
                                                .size(28.dp),
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        Text(
                                            text = detailedItem.diseaseName ?: "Здоровое растение"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!deleteWarning) {
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
                        deleteWarning = true
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
}