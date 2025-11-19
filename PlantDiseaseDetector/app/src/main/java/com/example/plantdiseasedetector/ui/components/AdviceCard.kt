package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdviceCard(
    number: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomEnd = 8.dp,
                                bottomStart = 0.dp
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }
    }
}