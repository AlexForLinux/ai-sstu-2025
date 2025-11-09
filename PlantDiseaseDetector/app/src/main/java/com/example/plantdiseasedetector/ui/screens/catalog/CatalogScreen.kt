package com.example.plantdiseasedetector.ui.screens.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.data.model.Disease

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(viewModel: DiseaseVM, onDiseaseClick: (Disease) -> Unit) {

    val diseases : List<Disease> = viewModel.diseases.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Список заболеваний") }) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(diseases) { disease ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onDiseaseClick(disease) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = disease.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}