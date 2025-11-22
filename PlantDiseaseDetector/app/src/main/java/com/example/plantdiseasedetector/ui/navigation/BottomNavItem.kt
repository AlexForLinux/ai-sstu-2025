package com.example.myapp.ui.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.plantdiseasedetector.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val drawableId: Int
) {
    object Catalog : BottomNavItem("catalog", "Каталог", R.drawable.catalog)
    object Classify : BottomNavItem("class", "Диагностика", R.drawable.scan)
    object History : BottomNavItem("history", "История", R.drawable.history)
}
