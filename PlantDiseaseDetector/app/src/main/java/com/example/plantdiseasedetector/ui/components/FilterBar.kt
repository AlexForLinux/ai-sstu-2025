package com.example.plantdiseasedetector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.ui.screens.catalog.FilterState

@Composable
fun FilterBar(
    modifier : Modifier = Modifier,
    state: Boolean?,
    onState: (Boolean?) -> Unit,
    description: String? = null,
    iconChosen: Int =  R.drawable.filledbookmark,
    iconNotChosen: Int = R.drawable.crossedfilledbookmark,
) {

    var state by remember { mutableStateOf(state) }

    IconButton(
        onClick = {
            when (state) {
                null -> {
                    state = true
                    onState(true)
                }
                true -> {
                    state = false
                    onState(false)
                }
                false -> {
                    state = null
                    onState(null)
                }
            }
        },
        modifier = modifier
            .background(
                color = if (state == null)
                            Color.LightGray
                        else
                            MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Icon(
            painter = painterResource(
                id = if (state == false)
                        iconNotChosen
                    else
                        iconChosen
            ),
            contentDescription = description,
            tint = if (state == null)
                        Color.Gray
                    else
                        MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        )
    }
}