package cy.volleybolley.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cy.volleybolley.core.presentation.ui.model.VolleyColor

private val LightColorScheme = lightColorScheme(
    primary = VolleyColor.GreenForGradient,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onSurface = VolleyColor.TextCalendarDark,
    onSurfaceVariant = VolleyColor.TextCalendarLightGrey,
    outline = VolleyColor.TextCalendarLightGrey,
)

@Composable
fun VolleybolleyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
