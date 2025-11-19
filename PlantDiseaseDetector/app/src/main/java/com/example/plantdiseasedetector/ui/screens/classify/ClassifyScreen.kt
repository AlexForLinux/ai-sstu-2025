package com.example.plantdiseasedetector.ui.screens.classify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.plantdiseasedetector.R
import com.example.plantdiseasedetector.ui.components.ErrorCard
import com.example.plantdiseasedetector.ui.components.LoadingBox

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
@Composable
fun ClassifyScreen(
    viewModel: ClassifyVM = hiltViewModel()
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
            text = "Диагностика",
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
                    text = "Выберите изображение",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
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

        Spacer(modifier = Modifier.height(16.dp))

        when(val state = predictionsState){
            PredictionDataState.EmptyData -> {}
            is PredictionDataState.Loading -> {
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
                val predictions = state.item

                Column(
                    modifier = Modifier.width(288.dp)
                ) {
                    Text(
                        "Результаты диагностики",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    LazyColumn (
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(predictions.size) { idx ->

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(
                                                MaterialTheme.colorScheme.primary
                                            ),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            (idx + 1).toString(),
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(predictions[idx].diseaseClass)
                                }

                                Box(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(80.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            MaterialTheme.colorScheme.primary
                                        ),
                                    contentAlignment = Alignment.Center,
                                ){
                                    Text(
                                        "${"%.2f".format(predictions[idx].precision)}%",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}