package com.example.plantdiseasedetector.ui.screens.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HistoryScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text("История", style = MaterialTheme.typography.headlineMedium)
    }
}