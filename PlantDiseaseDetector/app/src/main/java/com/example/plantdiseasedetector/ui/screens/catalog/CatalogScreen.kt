package com.example.plantdiseasedetector.ui.screens.catalog


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.ui.components.DiseaseCard
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CatalogScreen(
    onDiseaseClick: (Disease) -> Unit,
    viewModel: DiseaseVM = hiltViewModel())
{
    val diseases : List<Disease> by viewModel.diseases.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
    ){
        item {
            Text(
                text = "Каталог Заболеваний",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        items(diseases) { disease ->
            DiseaseCard(
                imageRes = R.drawable.ic_launcher_background,
                title = disease.name,
                description = disease.description,
                onNavigateClick = { onDiseaseClick(disease) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}