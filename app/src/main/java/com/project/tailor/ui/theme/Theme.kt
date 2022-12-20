package com.project.tailor.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ERROR = Color(0xFFCF6679)
val ANSWER_FOUND = Color(0xFF9FC587)

private val DarkColors = darkColors(
    primary = md_theme_dark_primary,
    secondary = md_theme_dark_secondary,
    error = ERROR,
    onSecondary = md_theme_dark_onSecondary,
    onSurface = md_theme_dark_onSurface,
    onPrimary = md_theme_dark_onPrimary

)
private val LightColors = lightColors(
    primary = md_theme_light_primary,
    secondary = md_theme_light_secondary,
    onSurface = md_theme_light_onSurface,
    onPrimary = md_theme_light_onPrimary,
    error = ERROR,
    onSecondary = md_theme_light_onSecondary
)

@Composable
fun TailorTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }
    MaterialTheme(
        colors = colors,
        content = content
    )
}