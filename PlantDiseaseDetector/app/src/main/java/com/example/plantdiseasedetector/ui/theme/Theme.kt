package com.example.plantdiseasedetector.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.plantdiseasedetector.ui.theme.Green500

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF81C784),          // светло-зелёный для акцентов
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE8F5E9),  // очень светлый зелёный
    onPrimaryContainer = Color(0xFF1B5E20),

    secondary = Color(0xFFFF9800),        // оранжевый для акцентов
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFFE65100),

    tertiary = Color(0xFFFFF59D),         // жёлтый для дополнительных элементов
    onTertiary = Color(0xFF000000),

    background = Color(0xFFFAFAFA),       // нейтральный светлый фон
    onBackground = Color(0xFF212121),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF212121),

    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFFFFF)
//    primary = Color(0xFFAED581),
//    primaryContainer = Color(0xFFDCEDC8),
//    onPrimaryContainer = Color(0xFF),
//
//    secondary = Color(0xFFFFECB3),
//    background = Color(0xFFFAFAFA),
//    surface = Color(0xFFF5F5F5)

    /* Other default colors to override

    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PlantDiseaseDetectorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}