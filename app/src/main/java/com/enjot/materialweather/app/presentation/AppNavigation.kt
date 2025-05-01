package com.enjot.materialweather.app.presentation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.enjot.materialweather.weather.presentation.ui.screen.daily.DailyScreen
import com.enjot.materialweather.weather.presentation.ui.screen.overview.OverviewScreenRoot
import com.enjot.materialweather.weather.presentation.ui.screen.settings.SettingsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Overview
    ) {

        val animationSpec: FiniteAnimationSpec<IntOffset> =
            spring(stiffness = Spring.StiffnessMediumLow)

        composable<Overview>(
            enterTransition = { slideIntoContainer(End, animationSpec) },
            exitTransition = { slideOutOfContainer(Start, animationSpec) }
        ) {
            OverviewScreenRoot(
                onNavigateToDailyWeather = { index ->
                    navController.navigate(Daily(index)) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(Settings) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Daily>(
            enterTransition = { slideIntoContainer(Start, animationSpec) },
            exitTransition = { slideOutOfContainer(End, animationSpec) }
        ) { backStackEntry ->
            val daily: Daily = backStackEntry.toRoute()
            DailyScreen(
                enterIndex = daily.index,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Settings>(
            enterTransition = { slideIntoContainer(Start, animationSpec) },
            exitTransition = { slideOutOfContainer(End, animationSpec) }
        ) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
