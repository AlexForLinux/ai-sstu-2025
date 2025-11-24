package com.example.plantdiseasedetector.ui.screens.history

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantdiseasedetector.ui.components.DiseaseCard
import com.example.plantdiseasedetector.ui.components.FilterBar
import com.example.plantdiseasedetector.ui.components.HistoryCard
import com.example.plantdiseasedetector.ui.components.LoadingBox
import com.example.plantdiseasedetector.ui.components.ReportCard
import com.example.plantdiseasedetector.ui.components.SearchBar
import com.example.plantdiseasedetector.ui.screens.catalog.DiseaseListState
import com.example.plantdiseasedetector.ui.screens.classify.ClassifyVM

@Composable
fun HistoryScreen(
    viewModel: HistoryVM = hiltViewModel(),
) {

    val historyState by viewModel.historyState.collectAsState()
    val imagesState by viewModel.imagesState.collectAsState()

    val onDelete = remember (viewModel) {
        {
            id: Long ->
            viewModel.deleteReport(id)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "История диагностик",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Spacer(Modifier.height(8.dp))

        when (val state = historyState) {

            is HistoryDataState.Loading -> {
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
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

            is HistoryDataState.Success -> {
                val reports = state.reports
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    reports.forEach {
                        detailedReport ->
                        ReportCard(
                            report = detailedReport,
                            image = imagesState[detailedReport.report.id],
                            onDelete = onDelete,
                            modifier = Modifier.padding(vertical = 8.dp),
                            colors = listOf(
                                MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    if (reports.isEmpty()) {
                        Text("Нет данных о диагностике")
                    }
                }
            }

            is HistoryDataState.Error -> {

            }
        }
    }

}