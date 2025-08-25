package com.seno.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Indigo800,
    onPrimary = Color.White,
    primaryContainer = Indigo100,
    onPrimaryContainer = Color(0xFF0A1B4D),

    secondary = Teal800,
    onSecondary = Color.White,
    secondaryContainer = Teal100,
    onSecondaryContainer = Color(0xFF00363C),

    tertiary = Amber700,
    onTertiary = Color.White,
    tertiaryContainer = Amber100,
    onTertiaryContainer = Color(0xFF371A00),

    background = InkLight,
    onBackground = Color(0xFF0B1220),

    surface = InkLight,
    onSurface = Color(0xFF0B1220),

    surfaceVariant = SurfaceVarLight,
    onSurfaceVariant = Color(0xFF414956),

    outline = Color(0xFF717784),
    outlineVariant = Color(0xFFC7CDD6),

    inverseSurface = Color(0xFF12161C),
    inverseOnSurface = Color(0xFFE6EAF0),
    inversePrimary = Blue300,

    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    scrim = Color(0x66000000)
)

private val DarkColors = darkColorScheme(
    primary = Blue300,
    onPrimary = Color(0xFF002B6B),
    primaryContainer = Indigo900,
    onPrimaryContainer = Color(0xFFD8E2FF),

    secondary = Color(0xFF67E8F9),
    onSecondary = Color(0xFF00363C),
    secondaryContainer = Color(0xFF004E56),
    onSecondaryContainer = Teal300,

    tertiary = Amber200,
    onTertiary = Color(0xFF3A2000),
    tertiaryContainer = Color(0xFF5A2D00),
    onTertiaryContainer = Color(0xFFFFDCBE),

    background = InkDark,
    onBackground = Color(0xFFDDE3EA),

    surface = InkDark2,
    onSurface = Color(0xFFDDE3EA),

    surfaceVariant = SurfaceVarDark,
    onSurfaceVariant = Color(0xFFB9C0CA),

    outline = Color(0xFF8B919B),
    outlineVariant = Color(0xFF3B424B),

    inverseSurface = Color(0xFFE6EAF0),
    inverseOnSurface = Color(0xFF14181E),
    inversePrimary = Indigo100,

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    scrim = Color(0x80000000)
)

@Composable
fun GencidevTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) dynamicDarkColorScheme(androidx.compose.ui.platform.LocalContext.current)
            else dynamicLightColorScheme(androidx.compose.ui.platform.LocalContext.current)
        } else {
            if (darkTheme) DarkColors else LightColors
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}