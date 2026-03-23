package cy.volleybolley.core.presentation.ui

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.Root
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField.MessageContainer
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil

object VolleyMessageTextField {
    @Composable
    fun MessageField(
        modifier: Modifier = Modifier,
        textInput: String,
        maxLength: Int = 160,
        hint: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        val limitedText = VolleyUiUtil.getLimitedText(maxLength, textInput)
        Box {
            MessageContainer(
                modifier = modifier
            ) {
                // Внутренний Box содержит и текстовое поле, и счётчик,
                // чтобы backgroundHeight учитывал их обеих
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                ) {
                    MessageTextField(
                        textInput = limitedText,
                        maxLength = maxLength,
                        hint = hint,
                        actionToTransferContent = { text ->
                            actionToTransferContent(text)
                        },
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 34.dp
                            )
                            .fillMaxWidth()
                    )

                    VolleyText.BodyLight(
                        text = "${limitedText.length}/$maxLength",
                        maxLines = 1,
                        color = VolleyColor.White,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(
                                bottom = 16.dp
                            )
                    )
                }
            }
        }
        LaunchedEffect(textInput) {
            if (textInput.length > maxLength) {
                actionToTransferContent(textInput.take(maxLength))
            }
        }
    }

    @Stable
    @Composable
    private fun MessageTextField(
        modifier: Modifier = Modifier,
        textInput: String,
        maxLength: Int,
        hint: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        Box(
            modifier = modifier
        ) {
            if (textInput.isEmpty()) {
                VolleyText.BodyLight(
                    text = hint,
                    color = VolleyColor.White,
                )
            }

            BasicTextField(
                value = textInput,
                onValueChange = { text ->
                    val limitedText = VolleyUiUtil.getLimitedText(maxLength, text)
                    actionToTransferContent(limitedText)
                },
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                textStyle = VolleyTypography.BodyRegular.copy(color = VolleyColor.White),
                cursorBrush = SolidColor(VolleyColor.White),
            )
        }
    }

    @Composable
    fun MessageContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = 24,
        cornerRadius: Int = 16,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        minHeight: Dp = 90.dp,
        content: @Composable BoxScope.() -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            MessageFieldGradientContainer(
                cornerRadius = cornerRadius,
                mainContainerAlignment = mainContainerAlignment,
                contentContainerAlignment = contentContainerAlignment,
                modifier = modifier,
                minHeight = minHeight,
                content = content
            )
        } else {
            MessageFieldBlurContainer(
                cornerRadius = cornerRadius,
                blurRadius = blurRadius,
                mainContainerAlignment = mainContainerAlignment,
                contentContainerAlignment = contentContainerAlignment,
                modifier = modifier,
                minHeight = minHeight,
                content = content
            )
        }
    }

    @Composable
    private fun MessageFieldBlurContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = 24,
        cornerRadius: Int = 16,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        minHeight: Dp,
        content: @Composable BoxScope.() -> Unit
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)
        var containerSize by remember { mutableStateOf(IntSize.Zero) } //

        Box(
            contentAlignment = mainContainerAlignment,
            modifier = modifier
                // измерим размер контейнера (нужно для отладки / если понадобится)//
                .onSizeChanged { containerSize = it }
                .clip(shape)
        ) {
            // фон растягиваем на весь размер родителя — matchParentSize()
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(VolleyColor.White.copy(alpha = 0.08f))
                    .blur(blurRadius.dp)
                    .border(
                        width = 4.dp,
                        color = VolleyColor.White.copy(alpha = 0.2f),
                        shape = shape
                    )
            )

            // контент поверх того же родителя
            MessageContentBox(
                screenDensity = LocalDensity.current,
                contentContainerAlignment = contentContainerAlignment,
                minHeight = minHeight,
                content = content,
                // можно оставить пустым callback'ом, если он больше не нужен
                setBackgroundHeightCallback = {}
            )
        }
    }

    @Composable
    private fun MessageFieldGradientContainer(
        modifier: Modifier = Modifier,
        cornerRadius: Int = 16,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        minHeight: Dp,
        content: @Composable BoxScope.() -> Unit
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)

        var containerSize by remember { mutableStateOf(IntSize.Zero) }
        var gradientRadius = VolleyUiUtil.getGradientRadiusByContainerSize(containerSize)

        Box(
            contentAlignment = mainContainerAlignment,
            modifier = modifier
                .onSizeChanged { containerSize = it }
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.radialGradient(
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_1 to VolleyColor.White
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_1),
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_2 to VolleyColor.White
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_2),
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_3 to VolleyColor.White
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_3),
                            center = Offset(containerSize.width / 2f, containerSize.height / 2f),
                            radius = gradientRadius,
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = VolleyColor.White.copy(alpha = VolleyUiUtil.GRADIENT_BORDER_ALPHA),
                        shape = shape
                    )
            )

            MessageContentBox(
                screenDensity = LocalDensity.current,
                contentContainerAlignment = contentContainerAlignment,
                minHeight = minHeight,
                content = content,
                setBackgroundHeightCallback = {}
            )
        }
    }

    @Stable
    @Composable
    private fun MessageContentBox(
        modifier: Modifier = Modifier,
        screenDensity: Density,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        setBackgroundHeightCallback: (Dp) -> Unit,
        minHeight: Dp,
        content: @Composable BoxScope.() -> Unit
    ) {
        Box(
            modifier = modifier
                .onSizeChanged { size ->
                    val pxHeight = size.height
                    val dpHeight = with(screenDensity) { pxHeight.toDp() }
                    setBackgroundHeightCallback(
                        if (dpHeight < minHeight) minHeight else dpHeight
                    )
                },
            contentAlignment = contentContainerAlignment,
            content = content
        )
    }

    @Composable
    fun MessageBubble(
        text: String,
        modifier: Modifier = Modifier,
        maxLength: Int = Int.MAX_VALUE
    ) {
        val limited = remember(text) { VolleyUiUtil.getLimitedText(maxLength, text) }

        MessageContainer(
            modifier = modifier,
            minHeight = 52.dp
        ) {
            VolleyText.BodyRegular(
                text = limited,
                color = VolleyColor.White,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewMessageField() {
    Root {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Spacer(modifier = Modifier.height(44.dp))

            VolleyMessageTextField.MessageField(
                hint = "Some hint...",
                textInput = "",
                modifier = Modifier
                    .height(106.dp)
                    .padding(20.dp, 0.dp)
            ) { }

            Spacer(modifier = Modifier.height(24.dp))

            VolleyMessageTextField.MessageField(
                hint = "Some hint...",
                textInput = stringResource(R.string.lorem_ipsum),
                modifier = Modifier
                    .padding(20.dp, 0.dp)
            ) { }

            Spacer(modifier = Modifier.height(24.dp))

            MessageContainer(
                modifier = Modifier
                    .padding(20.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                VolleyText.BodySmall(
                    text = "This is a simple bubble preview using MessageContainer",
                    color = VolleyColor.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
