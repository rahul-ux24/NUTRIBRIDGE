package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NutriBridgeLightColorScheme = lightColorScheme(
  primary = DeepForestGreen,
  onPrimary = CardWhite,
  primaryContainer = SageLight,
  onPrimaryContainer = DeepForestGreen,
  secondary = MutedSage,
  onSecondary = Charcoal,
  secondaryContainer = SageLight,
  onSecondaryContainer = DeepForestGreen,
  tertiary = SoftGold,
  onTertiary = Charcoal,
  tertiaryContainer = GoldLight,
  onTertiaryContainer = Charcoal,
  background = WarmIvory,
  onBackground = Charcoal,
  surface = CardWhite,
  onSurface = Charcoal,
  surfaceVariant = SageLight,
  onSurfaceVariant = CharcoalSecondary,
  outline = BorderSubtle,
  error = ErrorRed,
  onError = CardWhite,
)

private val NutriBridgeDarkColorScheme = darkColorScheme(
  primary = MutedSage,
  onPrimary = DeepForestGreen,
  primaryContainer = DeepForestGreen,
  onPrimaryContainer = SageLight,
  secondary = SoftGold,
  onSecondary = Charcoal,
  background = Color(0xFF141F1C),
  onBackground = WarmIvory,
  surface = Color(0xFF1B2A26),
  onSurface = WarmIvory,
  surfaceVariant = Color(0xFF233530),
  onSurfaceVariant = SageLight,
  outline = Color(0xFF334A43),
  error = ErrorRed,
  onError = CardWhite,
)

@Composable
fun NutriBridgeTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) NutriBridgeDarkColorScheme else NutriBridgeLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

