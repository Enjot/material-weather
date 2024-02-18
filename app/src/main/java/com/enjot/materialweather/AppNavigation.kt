package com.enjot.materialweather

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.enjot.materialweather.ui.daily.DailyScreen
import com.enjot.materialweather.ui.overviewscreen.OverviewScreen
import com.enjot.materialweather.ui.settings.SettingsScreen

@Composable
fun AppNavigation() {
    
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "overview") {
        composable("overview") {
            OverviewScreen(
                onNavigateToDailyWeather = { index -> navController.navigate("daily/$index") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable(
            "daily/{dailyIndex}",
            arguments = listOf(navArgument("dailyIndex") { type = NavType.IntType })
        ) {
            DailyScreen(it.arguments?.getInt("dailyIndex"))
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}
