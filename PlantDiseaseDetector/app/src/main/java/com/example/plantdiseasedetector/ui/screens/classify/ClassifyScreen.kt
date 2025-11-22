package com.example.plantdiseasedetector.ui.screens.classify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.data.model.Disease
import com.example.plantdiseasedetector.data.model.PrecisionLevel
import com.example.plantdiseasedetector.ui.components.ErrorCard
import com.example.plantdiseasedetector.ui.components.LoadingBox
import com.example.plantdiseasedetector.ui.components.PrecisionPieChart
import com.example.plantdiseasedetector.ui.components.TextWithLinks
import com.example.plantdiseasedetector.ui.functions.generateGreenColors
import com.example.plantdiseasedetector.ui.functions.uriToBitmap

@Composable
fun ClassifyScreen(
    viewModel: ClassifyVM = hiltViewModel(),
    onDiseaseClick: (String) -> Unit
) {
    val predictionsState by viewModel.predictionsState.collectAsState()

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = uriToBitmap(context, it)
            viewModel.setBitmap(bitmap)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        viewModel.setBitmap(bitmap)
    }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "ИИ-Диагностика",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Box(
            modifier = Modifier
                .size(288.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(16.dp)
                )
                .background(
                    MaterialTheme.colorScheme.primaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.loadedBitmap != null) {
                AsyncImage(
                    model = viewModel.loadedBitmap,
                    contentDescription = "Выбранное фото",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "Выберите изображение для анализа ИИ-моделью",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
            ) {
                IconButton(
                    onClick = { cameraLauncher.launch() },
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = "Сделать фото",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                Spacer(modifier = Modifier.width(64.dp))

                IconButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gallery),
                        contentDescription = "Выбрать из галереи",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.predict()
            },
            modifier = Modifier.width(288.dp),
            enabled = viewModel.loadedBitmap != null
        ) {
            Text("Диагностика")
        }

        when(val state = predictionsState){
            PredictionDataState.EmptyData -> {}
            is PredictionDataState.Loading -> {
                Spacer(modifier = Modifier.height(16.dp))
                LoadingBox()
            }
            is PredictionDataState.Error -> {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    ErrorCard(
                        title = "Ошибка обработки изображения",
                        description = state.message
                    )
                }
            }
            is PredictionDataState.Success -> {
                val modelPrediction = state.item
                val precisions = modelPrediction.precisions
                val myColors = generateGreenColors(precisions.size)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Результаты диагностики",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(8.dp))

                    Card (
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                                .fillMaxWidth()
                        )
                        {
                            val diseases = (precisions.associate { precision ->
                                precision.diseaseId to precision.name
                            } - "healthy")

                            when (state.item.precisionLevel) {

                                PrecisionLevel.HIGH -> {
                                    TextWithLinks(
                                        "Вероятный диагноз: ${precisions[0].name}",
                                        diseases,
                                        onDiseaseClick = { s -> onDiseaseClick(s) },
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Justify
                                    )
                                }

                                PrecisionLevel.MEDIUM -> {
                                    TextWithLinks(
                                        "Вероятный диагноз: ${precisions[0].name}\n" +
                                                "Однако есть основания для другого диагноза: ${precisions[1].name}",
                                        diseases,
                                        onDiseaseClick = { s -> onDiseaseClick(s) },
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Justify
                                    )
                                }

                                PrecisionLevel.LOW -> {
                                    Text(
                                        "Не удалось однозначно определить диагноз. Попробуйте сделать фотограию при лучшем освещении или с другого ракурса.",
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Card (
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PrecisionPieChart(
                                precisions.map { it.precision },
                                modifier = Modifier.size(128.dp),
                                defaultColors = myColors,
                            )

                            Spacer(Modifier.width(8.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                precisions.forEachIndexed { idx, precision ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween

                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(
                                                        myColors[idx]
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    (idx + 1).toString(),
                                                    color = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(4.dp))

                                            Text(precision.name)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}