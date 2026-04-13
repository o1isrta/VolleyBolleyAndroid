package cy.volleybolley.core.presentation.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyProgress.DefaultSize
import cy.volleybolley.core.presentation.ui.component.VolleyProgress.SmallSize
import cy.volleybolley.core.presentation.ui.model.VolleyColor

object VolleyProgress {
    /**
     * Default size for full-screen loading indicators
     */
    val DefaultSize = 80.dp

    /**
     * Small size for inline loading (e.g., inside buttons)
     */
    val SmallSize = 24.dp

    /**
     * Rotating volleyball progress indicator.
     *
     * @param size The size of the indicator. Use [DefaultSize] for full-screen or [SmallSize] for inline.
     * @param durationMillis Duration of one full rotation in milliseconds.
     */
    @Stable
    @Composable
    fun CircularProgress(
        modifier: Modifier = Modifier,
        size: Dp = DefaultSize,
        durationMillis: Int = 1000,
        colorTint: Color = VolleyColor.White
    ) {
        val rotation = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_ball),
            colorFilter = ColorFilter.tint(colorTint),
            contentDescription = null,
            modifier = modifier
                .size(size)
                .graphicsLayer {
                    rotationZ = rotation.value
                }
        )
    }

    /**
     * Small rotating volleyball progress indicator for inline use (e.g., inside buttons).
     * Uses faster animation for better UX in buttons.
     */
    @Stable
    @Composable
    fun SmallCircularProgress(
        modifier: Modifier = Modifier,
        durationMillis: Int = 600,
        colorTint: Color = VolleyColor.White
    ) {
        CircularProgress(
            modifier = modifier,
            size = SmallSize,
            durationMillis = durationMillis,
            colorTint = colorTint
        )
    }
}
