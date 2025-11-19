package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

@Composable
fun DiseaseCard(
    imageRes: Int,
    title: String,
    description: String,
    onNavigateClick: () -> Unit,
    modifier : Modifier = Modifier
) {

    var isMarked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(onClick =  onNavigateClick ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )


                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        modifier = Modifier.weight(3f)
                    )

                }
            }
        }

        IconButton(
            onClick = { isMarked = !isMarked},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (8).dp, y = (-8).dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
        ) {
            Icon(
                imageVector = if (isMarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Закладка",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}