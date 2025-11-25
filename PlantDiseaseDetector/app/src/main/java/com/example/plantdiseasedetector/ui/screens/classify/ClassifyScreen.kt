package com.example.plantdiseasedetector.ui.screens.classify

import android.net.Uri
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.ui.components.DiagnosticResult
import com.example.plantdiseasedetector.ui.components.ErrorCard
import com.example.plantdiseasedetector.ui.components.LoadingBox
import com.example.plantdiseasedetector.ui.functions.uriToBitmap

@Composable
fun ClassifyScreen(
    viewModel: ClassifyVM = hiltViewModel(),
    onDiseaseClick: (Long) -> Unit
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            contentAlignment = Alignment.Center,
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
                )
        ) {
            if (viewModel.loadedBitmap != null) {
                AsyncImage(
                    model = viewModel.loadedBitmap,
                    contentDescription = "Выбранное фото",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize(),
                )

                IconButton(
                    onClick = { viewModel.setBitmap(null) },
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.TopEnd)
                        .offset((-8).dp, 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Сделать фото",
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }

            } else {
                Text(
                    text = "Выберите изображение для анализа ИИ-моделью",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )

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
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.predict()
            },
            enabled = viewModel.loadedBitmap != null,
            modifier = Modifier
                .width(288.dp)
        ) {
            Text("Диагностика")
        }

        when(val state = predictionsState){
            is PredictionDataState.EmptyData -> {}

            is PredictionDataState.Loading -> {
                Spacer(modifier = Modifier.height(16.dp))
                LoadingBox()
            }

            is PredictionDataState.Error -> {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    ErrorCard(
                        title = "Ошибка обработки изображения",
                        description = state.message
                    )
                }
            }

            is PredictionDataState.Success -> {
                val modelPrediction = state.item
                val confidences = modelPrediction.confidences
                val confidenceLevel = modelPrediction.confidenceLevel
                val myColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary,
                )

                DiagnosticResult(
                    confidences = confidences,
                    confidenceLevel = confidenceLevel,
                    colors = myColors,
                    onDiseaseClick = onDiseaseClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}