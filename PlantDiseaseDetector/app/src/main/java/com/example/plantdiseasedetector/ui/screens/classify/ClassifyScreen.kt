package com.example.plantdiseasedetector.ui.screens.classify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        if (bitmap != null) {
            viewModel.setBitmap(bitmap)
        }
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
            if (viewModel.selectedImageBitmap != null) {
                AsyncImage(
                    model = viewModel.selectedImageBitmap,
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

            Row (
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

                IconButton (
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
            onClick = { /* Обработка моделью */ },
            modifier = Modifier.width(288.dp),
            enabled = viewModel.selectedImageBitmap != null
        ) {
            Text("Диагностика")
        }
    }
}