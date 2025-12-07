package com.example.plantdiseasedetector.ui.screens.catalog


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.ui.components.DiseaseCard
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plantdiseasedetector.ui.components.ErrorCard
import com.example.plantdiseasedetector.ui.components.FilterBar
import com.example.plantdiseasedetector.ui.components.LoadingBox
import com.example.plantdiseasedetector.ui.components.SearchBar

@Composable
fun CatalogScreen(
    onDiseaseClick: (Disease) -> Unit,
    viewModel: DiseaseVM = hiltViewModel())
{
    val diseaseState by viewModel.diseaseListState.collectAsState()
    val queryState by viewModel.queryState.collectAsState()
    val filterState by viewModel.filterState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.updateDiseaseList()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Каталог Заболеваний",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(Modifier.height(8.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SearchBar(
                onQuery = {
                    query -> viewModel.setQuery(query)
                    viewModel.updateDiseaseList()
                },
                query = queryState,
                modifier = Modifier
                    .weight(1f),
            )

            Spacer(Modifier.width(8.dp))

            FilterBar(
                onState = {
                    filter -> viewModel.setFilter(filter)
                    viewModel.updateDiseaseList()
                },
                state = filterState
            )
        }

        Spacer(Modifier.height(8.dp))

        when (val state = diseaseState) {

            is DiseaseListState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                )
                {
                    LoadingBox(size = 128.dp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Загрузка данных ...",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            is DiseaseListState.Success -> {
                val diseases = state.diseases

                if (diseases.isEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(
                            items = diseases,
                            key = {it.id}
                        ) { disease ->
                            DiseaseCard(
                                disease = disease,
                                onNavigateClick = onDiseaseClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            is DiseaseListState.Error -> {
                ErrorCard(
                    "Ошибка загрузки",
                    state.message
                )
            }
        }
    }
}