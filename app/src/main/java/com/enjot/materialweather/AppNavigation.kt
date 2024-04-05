package com.enjot.materialweather

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.enjot.materialweather.ui.dailyscreen.DailyScreen
import com.enjot.materialweather.ui.overviewscreen.OverviewScreen
import com.enjot.materialweather.ui.settingsscreen.SettingsScreen

@Composable
fun AppNavigation() {
    
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "overview"
    ) {
        
        
        
        composable(
            route = "overview",
            enterTransition = { slideIntoContainer(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow), towards = End) },
            exitTransition = { slideOutOfContainer(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow), towards = Start) },
        ) {
            OverviewScreen(
                onNavigateToDailyWeather = { index -> navController.navigate("daily/$index") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        
        composable(
            route = "daily/{dailyIndex}",
            enterTransition = { slideIntoContainer(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow), towards = Start) },
            exitTransition = { slideOutOfContainer(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow), towards = End) },
            arguments = listOf(navArgument("dailyIndex") { type = NavType.IntType })
        ) {
            DailyScreen(it.arguments?.getInt("dailyIndex"))
        }
        
        composable(
            route = "settings",
            enterTransition = { slideIntoContainer(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow), towards = Start) },
            exitTransition = { slideOutOfContainer(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow), towards = End) },
        ) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
