package com.enjot.materialweather.core.presentation.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF00658D)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFC6E7FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001E2D)
val md_theme_light_secondary = Color(0xFF4F616D)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFD2E5F4)
val md_theme_light_onSecondaryContainer = Color(0xFF0A1D28)
val md_theme_light_tertiary = Color(0xFF62597C)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFE8DEFF)
val md_theme_light_onTertiaryContainer = Color(0xFF1E1735)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFBFCFF)
val md_theme_light_onBackground = Color(0xFF191C1E)
val md_theme_light_surface = Color(0xFFFBFCFF)
val md_theme_light_onSurface = Color(0xFF191C1E)
val md_theme_light_surfaceVariant = Color(0xFFDDE3EA)
val md_theme_light_onSurfaceVariant = Color(0xFF41484D)
val md_theme_light_outline = Color(0xFF71787E)
val md_theme_light_inverseOnSurface = Color(0xFFF0F1F3)
val md_theme_light_inverseSurface = Color(0xFF2E3133)
val md_theme_light_inversePrimary = Color(0xFF81CFFF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF00658D)
val md_theme_light_outlineVariant = Color(0xFFC1C7CE)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFF81CFFF)
val md_theme_dark_onPrimary = Color(0xFF00344B)
val md_theme_dark_primaryContainer = Color(0xFF004C6B)
val md_theme_dark_onPrimaryContainer = Color(0xFFC6E7FF)
val md_theme_dark_secondary = Color(0xFFB6C9D8)
val md_theme_dark_onSecondary = Color(0xFF21333E)
val md_theme_dark_secondaryContainer = Color(0xFF374955)
val md_theme_dark_onSecondaryContainer = Color(0xFFD2E5F4)
val md_theme_dark_tertiary = Color(0xFFCCC1E9)
val md_theme_dark_onTertiary = Color(0xFF332C4C)
val md_theme_dark_tertiaryContainer = Color(0xFF4A4263)
val md_theme_dark_onTertiaryContainer = Color(0xFFE8DEFF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF191C1E)
val md_theme_dark_onBackground = Color(0xFFE2E2E5)
val md_theme_dark_surface = Color(0xFF191C1E)
val md_theme_dark_onSurface = Color(0xFFE2E2E5)
val md_theme_dark_surfaceVariant = Color(0xFF41484D)
val md_theme_dark_onSurfaceVariant = Color(0xFFC1C7CE)
val md_theme_dark_outline = Color(0xFF8B9298)
val md_theme_dark_inverseOnSurface = Color(0xFF191C1E)
val md_theme_dark_inverseSurface = Color(0xFFE2E2E5)
val md_theme_dark_inversePrimary = Color(0xFF00658D)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFF81CFFF)
val md_theme_dark_outlineVariant = Color(0xFF41484D)
val md_theme_dark_scrim = Color(0xFF000000)

val seed = Color(0xFFC6E7FF)


val ColorScheme.onProgressContainer: Color
    @Composable
    get() = MaterialTheme.colorScheme.primary.copy(
        alpha = 0.5f
    )

val ColorScheme.progressContainer: Color
    @Composable
    get() = MaterialTheme.colorScheme.surface.copy(
        alpha = 0.7f
    )