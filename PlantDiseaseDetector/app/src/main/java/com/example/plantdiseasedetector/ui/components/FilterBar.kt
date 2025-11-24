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
    onState: (Boolean?) -> Unit,
    description: String? = null,
    iconChosen: Int =  R.drawable.filledbookmark,
    iconNotChosen: Int = R.drawable.crossedfilledbookmark,
) {

    var state by remember { mutableStateOf(FilterState.NO_FILTER) }

    IconButton(
        onClick = {
            when (val st = state) {
                FilterState.NO_FILTER -> {
                    state = FilterState.MARKED
                    onState(true)
                }
                FilterState.MARKED -> {
                    state = FilterState.NOT_MARKED
                    onState(false)
                }
                FilterState.NOT_MARKED -> {
                    state = FilterState.NO_FILTER
                    onState(null)
                }
            }
        },
        modifier = modifier
            .background(
                color = if (state == FilterState.NO_FILTER)
                            Color.LightGray
                        else
                            MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Icon(
            painter = painterResource(
                id = if (state == FilterState.NOT_MARKED)
                        iconNotChosen
                    else
                        iconChosen
            ),
            contentDescription = description,
            tint = if (state == FilterState.NO_FILTER)
                        Color.Gray
                    else
                        MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        )
    }
}