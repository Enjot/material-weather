package com.enjot.materialweather

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
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
        
        val animationSpec: FiniteAnimationSpec<IntOffset> = spring(stiffness = Spring.StiffnessMediumLow)
        
        composable(
            route = "overview",
            enterTransition = { slideIntoContainer(End, animationSpec) },
            exitTransition = { slideOutOfContainer(Start, animationSpec) },
        ) {
            OverviewScreen(
                onNavigateToDailyWeather = { index -> navController.navigate("daily/$index") { launchSingleTop = true } },
                onNavigateToSettings = { navController.navigate("settings") { launchSingleTop = true } }
            )
        }
        
        composable(
            route = "daily/{dailyIndex}",
            enterTransition = { slideIntoContainer(Start, animationSpec) },
            exitTransition = { slideOutOfContainer(End, animationSpec) },
            arguments = listOf(navArgument("dailyIndex") { type = NavType.IntType })
        ) {
            DailyScreen(it.arguments?.getInt("dailyIndex"))
        }
        
        composable(
            route = "settings",
            enterTransition = { slideIntoContainer(Start, animationSpec) },
            exitTransition = { slideOutOfContainer(End, animationSpec) },
        ) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
