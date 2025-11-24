package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.R

@Composable
fun DiseaseCard(
    disease: Disease,
    onNavigateClick: (Disease) -> Unit,
    modifier : Modifier = Modifier
) {

    val onNavigateClickStable = remember(disease) {
        { onNavigateClick(disease) }
    }

    Box(
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onNavigateClickStable() }),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(8.dp),
            ) {

                Image(
                    painter = painterResource(id = disease.imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(112.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = disease.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                    )

                    Text(
                        text = disease.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                    )
                }
            }
        }

        if (disease.marked) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filledbookmark),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .aspectRatio(1f),
                )
            }
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-8).dp, y = (-8).dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f),
            )
        }
    }
}