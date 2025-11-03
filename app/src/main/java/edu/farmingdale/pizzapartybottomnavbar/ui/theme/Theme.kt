package edu.farmingdale.pizzapartybottomnavbar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

// ---------- Light Mode (Purples) ----------
private val LightColorScheme = lightColorScheme(
    primary = TruePurple,
    onPrimary = White,
    secondary = Orchid,
    onSecondary = White,
    tertiary = DarkViolet,
    onTertiary = White,
    background = Mauve,
    onBackground = Indigo,
    surface = Mauve,
    onSurface = Indigo
)

// ---------- Dark Mode (Blues) ----------
private val DarkColorScheme = darkColorScheme(
    primary = RoyalBlue,
    onPrimary = White,
    secondary = SteelBlue,
    onSecondary = White,
    tertiary = SkyBlue,
    onTertiary = NavyBlue,
    background = MidnightBlue,
    onBackground = BabyBlue,
    surface = NavyBlue,
    onSurface = SkyBlue
)

// ---------- App Theme ----------
@Composable
fun PizzaPartyBottomNavBarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
