package com.example.plantdiseasedetector.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.myapp.ui.navigation.BottomNavItem
import androidx.compose.ui.Modifier
import com.example.plantdiseasedetector.ui.screens.pages.CatalogScreen
import com.example.plantdiseasedetector.ui.screens.pages.ClassifyScreen
import com.example.plantdiseasedetector.ui.screens.pages.HistoryScreen

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
            composable("catalog") { CatalogScreen() }
            composable("class") { ClassifyScreen() }
            composable("history") { HistoryScreen() }
        }
    }
}