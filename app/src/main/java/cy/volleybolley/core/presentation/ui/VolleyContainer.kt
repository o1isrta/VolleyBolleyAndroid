package cy.volleybolley.core.presentation.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.ui.theme.VolleybolleyTheme

object VolleyContainer {
    const val GRADIENT_COLOR_STOP_MARK_1 = 0.0f
    const val GRADIENT_COLOR_STOP_MARK_2 = 0.65f
    const val GRADIENT_COLOR_STOP_MARK_3 = 1.0f
    const val GRADIENT_COLOR_STOP_VALUE_1 = 0.02f
    const val GRADIENT_COLOR_STOP_VALUE_2 = 0.2f
    const val GRADIENT_COLOR_STOP_VALUE_3 = 0.4f
    const val GRADIENT_BORDER_ALPHA = 0.05f

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
                    .background(VolleyColor.White.copy(alpha = 0.08f))
                    .clip(shape)
                    .blur(blurRadius.dp)
                    .border(
                        width = VolleyDimens.DIMEN_4.dp,
                        color = VolleyColor.White.copy(alpha = 0.2f),
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
                            GRADIENT_COLOR_STOP_MARK_1 to VolleyColor.White.copy(alpha = GRADIENT_COLOR_STOP_VALUE_1),
                            GRADIENT_COLOR_STOP_MARK_2 to VolleyColor.White.copy(alpha = GRADIENT_COLOR_STOP_VALUE_2),
                            GRADIENT_COLOR_STOP_MARK_3 to VolleyColor.White.copy(alpha = GRADIENT_COLOR_STOP_VALUE_3),
                            center = Offset(containerSize.width / 2f, containerSize.height / 2f),
                            radius = gradientRadius,
                        )
                    )
                    .clip(shape)
                    .border(
                        width = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.White.copy(alpha = GRADIENT_BORDER_ALPHA),
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

    @Composable
    fun GlassContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = VolleyDimens.DIMEN_24,
        cornerRadius: Int = VolleyDimens.DIMEN_32,
        contentAlignmentOnContainer: Alignment = Alignment.Center,
        content: @Composable BoxScope.() -> Unit
    ) {
        val localVew = LocalView.current
        var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
        val correctBlurRadius =
            if (blurRadius > 0 && blurRadius <= VolleyDimens.DIMEN_25) blurRadius else VolleyDimens.DIMEN_24
        val shape = RoundedCornerShape(cornerRadius.dp)

        Box(
            modifier = modifier
                .clip(shape)
                .border(1.dp, Color.White.copy(alpha = 0.1f), shape)
                .onGloballyPositioned { coordinates ->
                    val containerPositionInWindow = coordinates.positionInWindow()
                    val containerSize = coordinates.size

                    val rootView = localVew.rootView
                    val screenShot = createBitmap(rootView.width, rootView.height)
                    val canvas = Canvas(screenShot)
                    rootView.draw(canvas)

                    try {
                        val cropped = Bitmap.createBitmap(
                            screenShot,
                            containerPositionInWindow.x.toInt(),
                            containerPositionInWindow.y.toInt(),
                            containerSize.width,
                            containerSize.height
                        )
                        capturedImage = cropped
                    } catch (e: IllegalArgumentException) {
                        Log.e("UI_LOG", "Error cropping bitmap: ${e.message}")
                    }
                }

        ) {
            capturedImage?.let { backgroundImage ->
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    LegacyBlurImage(
                        bitmap = backgroundImage,
                        blurRadius = correctBlurRadius,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    BlurImage(
                        backgroundImage,
                        Modifier
                            .fillMaxSize()
                            .blur(correctBlurRadius.dp)
                    )
                }
            }

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
    private fun LegacyBlurImage(
        bitmap: Bitmap,
        blurRadius: Int,
        modifier: Modifier
    ) {
        val renderScript = RenderScript.create(LocalContext.current)
        val bitmapAlloc = Allocation.createFromBitmap(renderScript, bitmap)
        ScriptIntrinsicBlur.create(renderScript, bitmapAlloc.element).apply {
            setRadius(blurRadius.toFloat())
            setInput(bitmapAlloc)
            forEach(bitmapAlloc)
        }
        bitmapAlloc.copyTo(bitmap)
        renderScript.destroy()
        BlurImage(bitmap, modifier)
    }

    @Composable
    private fun BlurImage(
        bitmap: Bitmap,
        modifier: Modifier,
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
        Box(
            modifier = modifier.background(Color.White.copy(alpha = 0.1f))
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewContainers() {
    VolleyContainer.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleyContainer.TransparentContainer(
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_116.dp)
                ) {
                    Text(
                        text = stringResource(R.string.preview_container_text),
                        color = VolleyColor.White,
                        fontSize = VolleyDimens.DIMEN_16.sp
                    )
                }

                VolleyContainer.GlassContainer(
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_116.dp)
                ) {
                    Text(
                        text = stringResource(R.string.preview_container_text),
                        color = VolleyColor.White,
                        fontSize = VolleyDimens.DIMEN_16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewContainersInsideOtherContainer() {
    VolleyContainer.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            VolleyContainer.TransparentContainer(
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
                    .fillMaxSize()
            ) {
                Column {
                    VolleyContainer.TransparentContainer(
                        modifier = Modifier
                            .padding(VolleyDimens.DIMEN_20.dp)
                            .fillMaxWidth()
                            .height(VolleyDimens.DIMEN_116.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.preview_container_text),
                            color = VolleyColor.White,
                            fontSize = VolleyDimens.DIMEN_20.sp
                        )
                    }

                    VolleyContainer.GlassContainer(
                        modifier = Modifier
                            .padding(VolleyDimens.DIMEN_20.dp)
                            .fillMaxWidth()
                            .height(VolleyDimens.DIMEN_116.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.preview_container_text),
                            color = VolleyColor.White,
                            fontSize = VolleyDimens.DIMEN_16.sp
                        )
                    }
                }
            }
        }
    }
}
