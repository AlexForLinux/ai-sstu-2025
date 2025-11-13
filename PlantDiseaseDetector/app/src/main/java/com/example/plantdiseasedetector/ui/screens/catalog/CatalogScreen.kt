package com.example.plantdiseasedetector.ui.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.ui.components.DiseaseCard
import com.example.plantdiseasedetector.ui.components.PlantTopBar

import androidx.hilt.navigation.compose.hiltViewModel

import dagger.hilt.android.AndroidEntryPoint


@Composable
fun CatalogScreen(
    onDiseaseClick: (Disease) -> Unit,
    viewModel: DiseaseVM = hiltViewModel())
{

    val diseases : List<Disease> = viewModel.diseases.collectAsState().value

    Scaffold(
        topBar = {
            PlantTopBar("Каталог заболеваний", 16)
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(diseases) { disease ->
                DiseaseCard(
                    imageRes = R.drawable.ic_launcher_background,
                    title = disease.title,
                    description = disease.shortDesc,
                    onNavigateClick = { onDiseaseClick(disease) }
                )
            }
        }
    }
}