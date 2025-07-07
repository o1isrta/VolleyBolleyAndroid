package cy.volleybolley.core.presentation.ui

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.ui.theme.VolleybolleyTheme

object VolleyContainer {

    @Composable
    fun Root(content: @Composable (PaddingValues) -> Unit) {
        VolleybolleyTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    content(paddingValues)

                }
            }
        }
    }

    @Composable
    fun TransparentContainer(
        modifier: Modifier = Modifier,
        contentAlignmentOnContainer: Alignment = Alignment.Center,
        content: @Composable BoxScope.() -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            TransparentGradientContainer(
                contentAlignmentOnContainer = contentAlignmentOnContainer,
                modifier = modifier,
                content = content
            )
        } else {
            TransparentBlurContainer(
                contentAlignmentOnContainer = contentAlignmentOnContainer,
                modifier = modifier,
                content = content
            )
        }
    }

    @Composable
    private fun TransparentBlurContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = VolleyDimens.DIMEN_24,
        cornerRadius: Int = VolleyDimens.DIMEN_32,
        contentAlignmentOnContainer: Alignment = Alignment.Center,
        content: @Composable BoxScope.() -> Unit
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)

        Box(
            contentAlignment = contentAlignmentOnContainer,
            modifier = modifier
                .clip(shape)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(VolleyColor.WHITE.copy(alpha = 0.1f))
                    .clip(shape)
                    .blur(blurRadius.dp)
                    .border(
                        width = VolleyDimens.DIMEN_4.dp,
                        color = VolleyColor.WHITE.copy(alpha = 0.45f),
                        shape = shape
                    ),
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                contentAlignment = contentAlignmentOnContainer,
                content = content
            )
        }
    }

    @Composable
    private fun TransparentGradientContainer(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_32,
        contentAlignmentOnContainer: Alignment = Alignment.Center,
        content: @Composable BoxScope.() -> Unit
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)
        var containerSize by remember { mutableStateOf(IntSize.Zero) }
        var gradientRadius by remember { mutableFloatStateOf(1f) }

        Box(
            contentAlignment = contentAlignmentOnContainer,
            modifier = modifier
                .clip(shape)
        ) {

            Box(
                modifier = Modifier
                    .onSizeChanged { size ->
                        containerSize = size
                        gradientRadius = maxOf(size.width, size.height).toFloat()
                    }
                    .fillMaxSize()
                    .background(
                        shape = shape,
                        brush = Brush.radialGradient(
                            0.0f to VolleyColor.WHITE.copy(alpha = 0.02f),
                            0.65f to VolleyColor.WHITE.copy(alpha = 0.2f),
                            1.0f to VolleyColor.WHITE.copy(alpha = 0.4f),
                            center = Offset(containerSize.width / 2f, containerSize.height / 2f),
                            radius = gradientRadius,
                        )
                    )
                    .clip(shape)
                    .border(
                        width = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.WHITE.copy(alpha = 0.05f),
                        shape = shape
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape),
                contentAlignment = contentAlignmentOnContainer,
                content = content
            )
        }
    }

}