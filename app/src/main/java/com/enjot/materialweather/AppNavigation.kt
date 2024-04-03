package com.enjot.materialweather

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.enjot.materialweather.ui.dailyscreen.DailyScreen
import com.enjot.materialweather.ui.overviewscreen.OverviewScreen
import com.enjot.materialweather.ui.settings.SettingsScreen

@Composable
fun AppNavigation() {
    
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        startDestination = "overview"
    ) {
        
        composable(
            route = "overview"
        ) {
            OverviewScreen(
                onNavigateToDailyWeather = { index -> navController.navigate("daily/$index") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        
        composable(
            route = "daily/{dailyIndex}",
            arguments = listOf(navArgument("dailyIndex") { type = NavType.IntType })
        ) {
            DailyScreen(it.arguments?.getInt("dailyIndex"))
        }
        
        composable(
            route = "settings"
        ) {
            SettingsScreen()
        }
    }
}
