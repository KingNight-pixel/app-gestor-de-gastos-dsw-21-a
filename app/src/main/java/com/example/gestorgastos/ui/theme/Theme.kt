package com.example.gestorgastos.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EsmeraldaPrimarioOsc,
    onPrimary = SobrePrimarioOsc,
    primaryContainer = ContenedorPrimarioOsc,
    onPrimaryContainer = SobreContenedorPrimarioOsc,
    secondary = Color(0xFFA0CFCC),
    onSecondary = Color(0xFF003735),
    secondaryContainer = Color(0xFF1F4E4C),
    onSecondaryContainer = Color(0xFFBBECE8),
    tertiary = Color(0xFFFFB68F),
    onTertiary = Color(0xFF521E00),
    tertiaryContainer = Color(0xFF783100),
    onTertiaryContainer = Color(0xFFFFDCC3),
    background = FondoOscuro,
    onBackground = Color(0xFFDFE4DF),
    surface = SuperficieOscura,
    onSurface = Color(0xFFDFE4DF),
    surfaceVariant = SuperficieVarianteOsc,
    onSurfaceVariant = SobreVarianteOsc,
    outline = ContornoOsc,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

private val LightColorScheme = lightColorScheme(
    primary = EsmeraldaPrimario,
    onPrimary = SobrePrimario,
    primaryContainer = ContenedorPrimario,
    onPrimaryContainer = SobreContenedorPrimario,
    secondary = TealSecundario,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = ContenedorSecundario,
    onSecondaryContainer = SobreContenedorSecundario,
    tertiary = AmbarTerciario,
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = ContenedorTerciario,
    onTertiaryContainer = Color(0xFF311300),
    background = FondoClaro,
    onBackground = Color(0xFF171D1A),
    surface = SuperficieClara,
    onSurface = Color(0xFF171D1A),
    surfaceVariant = SuperficieVariante,
    onSurfaceVariant = SobreVariante,
    outline = Contorno,
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun GestorGastosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
