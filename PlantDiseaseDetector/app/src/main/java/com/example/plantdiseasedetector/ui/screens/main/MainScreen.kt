package com.example.plantdiseasedetector.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.myapp.ui.navigation.BottomNavItem
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.plantdiseasedetector.data.datasource.local.db.LocalDataBase
import com.example.plantdiseasedetector.data.repository.DiseaseRepositoryImpl
import com.example.plantdiseasedetector.ui.screens.catalog.CatalogScreen
import com.example.plantdiseasedetector.ui.screens.catalog.DiseaseDetailScreen
import com.example.plantdiseasedetector.ui.screens.catalog.DiseaseVM
import com.example.plantdiseasedetector.ui.screens.classify.ClassifyScreen
import com.example.plantdiseasedetector.ui.screens.history.HistoryScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MainScreen() {

    //TODO: Temporary decision
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
                CatalogScreen(
                    onDiseaseClick = { disease ->
                        navController.navigate("detail/${disease.id}")
                    }
                )
            }
            composable(
                route = "detail/{diseaseId}",
                arguments = listOf(navArgument("diseaseId") { type = NavType.IntType })
            ) { backStackEntry ->
                val diseaseId = backStackEntry.arguments?.getInt("diseaseId")
                DiseaseDetailScreen(diseaseId = diseaseId)
            }
            composable("class") { ClassifyScreen() }
            composable("history") { HistoryScreen() }
        }
    }
}