package com.example.myapp.ui.navigation

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
