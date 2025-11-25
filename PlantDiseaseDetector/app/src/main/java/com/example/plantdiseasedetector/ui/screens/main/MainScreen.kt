package com.example.plantdiseasedetector.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.myapp.ui.navigation.BottomNavItem
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.plantdiseasedetector.ui.screens.catalog.CatalogScreen
import com.example.plantdiseasedetector.ui.screens.detail.DiseaseDetailScreen
import com.example.plantdiseasedetector.ui.screens.classify.ClassifyScreen
import com.example.plantdiseasedetector.ui.screens.history.HistoryScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Catalog,
        BottomNavItem.Classify,
        BottomNavItem.History
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                    .background(color = MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
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
                        colors =  NavigationBarItemColors(
                            selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                            disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        label = { Text(item.title) },
                        icon = {
                            Icon(
                                painter = painterResource(item.drawableId),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        },
                        modifier = Modifier
                            .padding(top=8.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "catalog",
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable("catalog") {
                CatalogScreen(
                    onDiseaseClick = { disease ->
                        navController.navigate("detail/${disease.id}")
                    }
                )
            }

            composable(
                route = "detail/{diseaseId}",
                arguments = listOf(
                    navArgument("diseaseId") {
                        type = NavType.LongType
                    }
                ),
            ) {
                backStackEntry ->
                val diseaseId = backStackEntry.arguments?.getLong("diseaseId")
                DiseaseDetailScreen(diseaseId = diseaseId)
            }

            composable("class") {
                ClassifyScreen(
                    onDiseaseClick = { id ->
                        navController.navigate("detail/${id}")
                    }
                )
            }

            composable("history") { HistoryScreen() }
        }
    }
}