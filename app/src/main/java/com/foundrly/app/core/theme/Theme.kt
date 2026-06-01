package com.foundrly.app.core.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkWineColorScheme = darkColorScheme(
    primary             = WineAccent,
    onPrimary           = WineText,
    primaryContainer    = WinePrimary,
    onPrimaryContainer  = WineText,
    secondary           = WinePrimary,
    onSecondary         = WineText,
    secondaryContainer  = WineSurfaceVariant,
    onSecondaryContainer= WineText,
    tertiary            = WineSuccess,
    background          = WineBackground,
    onBackground        = WineOnBackground,
    surface             = WineSurface,
    onSurface           = WineOnSurface,
    surfaceVariant      = WineSurfaceVariant,
    onSurfaceVariant    = WineOnSurfaceVariant,
    outline             = WineOutline,
    outlineVariant      = WineOutlineVariant,
    error               = WineError,
    onError             = WineText,
    inverseSurface      = WineText,
    inverseOnSurface    = WineBackground,
    scrim               = Color.Black
)

@Composable
fun FoundrlyTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkWineColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = WineBackground.toArgb()
            window.navigationBarColor = WineBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
