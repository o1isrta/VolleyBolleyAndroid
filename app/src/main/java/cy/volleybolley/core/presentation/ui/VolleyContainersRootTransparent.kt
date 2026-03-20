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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.ui.theme.VolleybolleyTheme

object VolleyContainersRootTransparent {
    /**
     * Maximum blur radius supported by RenderScript ScriptIntrinsicBlur.
     * Used for legacy blur on Android < S (API 31).
     * @see android.renderscript.ScriptIntrinsicBlur
     */
    private const val RENDERSCRIPT_MAX_BLUR_RADIUS = 25

    /**
     * Default blur radius used when the provided value is out of valid range.
     */
    private const val DEFAULT_BLUR_RADIUS = 24
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
        blurRadius: Int = 24,
        cornerRadius: Int = 32,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            TransparentGradientContainer(
                cornerRadius = cornerRadius,
                mainContainerAlignment = mainContainerAlignment,
                contentContainerAlignment = contentContainerAlignment,
                modifier = modifier,
                content = content
            )
        } else {
            TransparentBlurContainer(
                cornerRadius = cornerRadius,
                blurRadius = blurRadius,
                mainContainerAlignment = mainContainerAlignment,
                contentContainerAlignment = contentContainerAlignment,
                modifier = modifier,
                content = content
            )
        }
    }

    @Composable
    private fun TransparentBlurContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = 24,
        cornerRadius: Int = 32,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit
    ) {
        val density = LocalDensity.current
        val shape = RoundedCornerShape(cornerRadius.dp)

        var backgroundSize by remember { mutableStateOf(IntSize.Zero) }
        val dpWidth = with(density) { backgroundSize.width.toDp() }
        val dpHeight = with(density) { backgroundSize.height.toDp() }

        Box(
            contentAlignment = mainContainerAlignment,
            modifier = modifier
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .size(dpWidth, dpHeight)
                    .background(VolleyColor.White.copy(alpha = 0.08f))
                    .clip(shape)
                    .blur(blurRadius.dp)
                    .border(
                        width = 4.dp,
                        color = VolleyColor.White.copy(alpha = 0.2f),
                        shape = shape
                    )
            )

            ContentBox(
                contentContainerAlignment = contentContainerAlignment,
                content = content,
            ) { size -> backgroundSize = size }
        }
    }

    @Composable
    private fun TransparentGradientContainer(
        modifier: Modifier = Modifier,
        cornerRadius: Int = 32,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit
    ) {
        val density = LocalDensity.current
        val shape = RoundedCornerShape(cornerRadius.dp)

        var backgroundSize by remember { mutableStateOf(IntSize.Zero) }
        val dpWidth = with(density) { backgroundSize.width.toDp() }
        val dpHeight = with(density) { backgroundSize.height.toDp() }
        val gradientRadius = VolleyUiUtil.getGradientRadiusByContainerSize(backgroundSize)

        Box(
            contentAlignment = mainContainerAlignment,
            modifier = modifier
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .size(dpWidth, dpHeight)
                    .background(
                        shape = shape,
                        brush = Brush.radialGradient(
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_1 to VolleyColor.White
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_1),
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_2 to VolleyColor.White
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_2),
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_3 to VolleyColor.White
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_3),
                            center = Offset(backgroundSize.width / 2f, backgroundSize.height / 2f),
                            radius = gradientRadius,
                        )
                    )
                    .clip(shape)
                    .border(
                        width = 1.dp,
                        color = VolleyColor.White.copy(alpha = VolleyUiUtil.GRADIENT_BORDER_ALPHA),
                        shape = shape
                    )
            )

            ContentBox(
                contentContainerAlignment = contentContainerAlignment,
                content = content,
            ) { size -> backgroundSize = size }
        }
    }

    @Composable
    private fun ContentBox(
        modifier: Modifier = Modifier,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit,
        setBackgroundHeightCallback: (IntSize) -> Unit,
    ) {
        Box(
            modifier = modifier
                .onSizeChanged { size ->
                    setBackgroundHeightCallback(size)
                },
            contentAlignment = contentContainerAlignment,
            content = content
        )
    }

    @Composable
    fun GlassContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = 24,
        cornerRadius: Int = 32,
        contentAlignmentOnContainer: Alignment = Alignment.Center,
        content: @Composable BoxScope.() -> Unit
    ) {
        val localVew = LocalView.current
        var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
        val correctBlurRadius = if (blurRadius in 1..RENDERSCRIPT_MAX_BLUR_RADIUS) {
            blurRadius
        } else {
            DEFAULT_BLUR_RADIUS
        }
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
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleyContainersRootTransparent.TransparentContainer(
                    cornerRadius = 16,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    VolleyText.BodyRegular(
                        text = "Some small text",
                        color = VolleyColor.White,
                        modifier = Modifier.padding(16.dp)

                    )
                }

                VolleyContainersRootTransparent.TransparentContainer(
                    cornerRadius = 16,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    VolleyText.BodyRegular(
                        text = "Text in max width",
                        color = VolleyColor.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .height(90.dp)
                            .fillMaxWidth()
                    )
                }

                VolleyContainersRootTransparent.TransparentContainer(
                    cornerRadius = 16,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column {
                        VolleyContainersRootTransparent.TransparentContainer(
                            cornerRadius = 8,
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            VolleyText.BodyRegular(
                                text = "some text 1",
                                color = VolleyColor.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        VolleyContainersRootTransparent.TransparentContainer(
                            cornerRadius = 8,
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            VolleyText.BodyRegular(
                                text = "some text 2",
                                color = VolleyColor.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                VolleyContainersRootTransparent.GlassContainer(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(116.dp)
                ) {
                    Text(
                        text = "Some glass container",
                        color = VolleyColor.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
