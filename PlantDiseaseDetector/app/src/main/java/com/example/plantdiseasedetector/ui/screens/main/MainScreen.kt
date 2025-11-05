package com.example.plantdiseasedetector.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.myapp.ui.navigation.BottomNavItem
import androidx.compose.ui.Modifier
import com.example.plantdiseasedetector.data.datasource.local.db.TestDataBase
import com.example.plantdiseasedetector.data.repository.DiseasePreviewRepositoryImpl
import com.example.plantdiseasedetector.ui.screens.catalog.CatalogScreen
import com.example.plantdiseasedetector.ui.screens.catalog.DiseasePreviewVM
import com.example.plantdiseasedetector.ui.screens.classify.ClassifyScreen
import com.example.plantdiseasedetector.ui.screens.history.HistoryScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Catalog,
        BottomNavItem.Classify,
        BottomNavItem.History
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.route == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        label = { Text(item.title) },
                        icon = { Icon(item.icon, contentDescription = null)}
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "catalog",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("catalog") {
                //TODO: Temporary decision
                val viewModel = DiseasePreviewVM(DiseasePreviewRepositoryImpl(TestDataBase()))
                CatalogScreen(viewModel)
            }
            composable("class") { ClassifyScreen() }
            composable("history") { HistoryScreen() }
        }
    }
}