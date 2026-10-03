package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = WinePrimary,
    onPrimary = Color.White,
    primaryContainer = WineCardBg,
    onPrimaryContainer = WinePrimary,
    secondary = WineLight,
    onSecondary = Color.White,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = WineCardBg,
    onSurfaceVariant = TextLabel,
    outline = WineCardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = WineLight,
    onPrimary = WineDark,
    primaryContainer = WineDark,
    onPrimaryContainer = Color.White,
    secondary = WinePrimary,
    onSecondary = Color.White,
    background = Color(0xFF1B1416),
    onBackground = Color(0xFFF3ECEE),
    surface = Color(0xFF261D20),
    onSurface = Color(0xFFF3ECEE),
    surfaceVariant = Color(0xFF3B2A30),
    onSurfaceVariant = Color(0xFFE2CED4),
    outline = Color(0xFF553C44)
)

@Composable
fun GestorGastosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Keep custom wine color palette consistent
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
