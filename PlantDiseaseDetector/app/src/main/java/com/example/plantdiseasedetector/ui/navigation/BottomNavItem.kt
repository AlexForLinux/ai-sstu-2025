package com.example.myapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Catalog : BottomNavItem("catalog", "Каталог", Icons.Default.Home)
    object Classify : BottomNavItem("class", "Диагностика", Icons.Default.Search)
    object History : BottomNavItem("history", "История", Icons.Default.Info)
}
