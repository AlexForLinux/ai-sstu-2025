package com.example.plantdiseasedetector.ui.screens.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.ui.components.PlantTopBar


@Composable
fun DiseaseDetailScreen(diseaseId: Int?, viewModel: DiseaseVM) {
    val disease = viewModel.getDiseaseById(diseaseId).collectAsState(initial = null).value

    Scaffold(
        topBar = {
            PlantTopBar("Диагноз", 16)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            if (disease != null) {
                Text(text = "Название: ${disease.title}")
                Text(text = "Описание: ${disease.shortDesc}")
            }
            else {
                Text(text = "Загрузка данных...")
            }
        }
    }
}
