package com.example.plantdiseasedetector.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantdiseasedetector.ui.components.AdviceCard
import com.example.plantdiseasedetector.ui.components.ErrorCard
import com.example.plantdiseasedetector.ui.components.LoadingBox
import com.example.plantdiseasedetector.ui.components.MarkableIcon

@Composable
fun DiseaseDetailScreen(diseaseId: String?, viewModel: DiseaseDetailVM = hiltViewModel()) {

    val diseaseState by viewModel.diseaseState.collectAsState()

    LaunchedEffect(diseaseId) {
        viewModel.loadDisease(diseaseId)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = diseaseState) {
            is DiseaseDataState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                )
                {
                    LoadingBox(size = 96.dp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Загрузка ...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
            is DiseaseDataState.Error -> {
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    ErrorCard(
                        title = "Ошибка загрузки данных: ",
                        description = state.message
                    )
                }
            }
            is DiseaseDataState.Success -> {
                val disease = state.item
                val onMarkChangedCallback = remember(viewModel) {
                    { mark: Boolean ->
                        viewModel.updateDiseaseMark(disease.id, mark)

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = disease.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    MarkableIcon(
                        marked = disease.marked,
                        onMarkChange = onMarkChangedCallback
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = disease.imageId),
                    contentDescription = "Фото заболевания",
                    modifier = Modifier
                        .size(288.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Text(
                    text = disease.description,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Советы по лечению",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    arrayOf(1, 2, 3).forEach { advice ->
                        AdviceCard(
                            advice,
                            "Title",
                            "Desc Desc Desc Desc Desc Desc Desc Desc sDesc Desc Desc",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        )
                    }
                }
            }
        }
    }
}
