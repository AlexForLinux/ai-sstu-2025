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
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetailScreen(diseaseId: Int?, viewModel: DiseaseVM = hiltViewModel()) {
    val disease = viewModel.getDiseaseById(diseaseId).collectAsState(initial = null).value

    Scaffold(
        topBar = { TopAppBar(title = { Text(disease?.title ?: "Детали заболевания") }) }
    ) { padding ->
        if (disease != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = "Название: ${disease.title}")
                Text(text = "Описание: ${disease.shortDesc}")
            }
        } else {
            Text(
                text = "Загрузка данных...",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
