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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.Root
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil

object VolleyMessageTextField {
    @Composable
    fun MessageField(
        modifier: Modifier = Modifier,
        textInput: String,
        maxLength: Int = VolleyDimens.DIMEN_160,
        hint: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        val limitedText = VolleyUiUtil.getLimitedText(maxLength, textInput)
        Box {
            MessageContainer(
                modifier = modifier
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
                            start = VolleyDimens.DIMEN_16.dp,
                            top = VolleyDimens.DIMEN_16.dp,
                            end = VolleyDimens.DIMEN_16.dp,
                            bottom = VolleyDimens.DIMEN_34.dp
                        )
                        .fillMaxWidth()
                )
            }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = modifier
                    .padding(
                        start = VolleyDimens.DIMEN_16.dp,
                        top = 0.dp,
                        end = VolleyDimens.DIMEN_16.dp,
                        bottom = VolleyDimens.DIMEN_16.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
            ) {
                VolleyText.BodyLight(
                    text = "${limitedText.length}/$maxLength",
                    maxLines = 1,
                    color = VolleyColor.WHITE,
                )
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
                    color = VolleyColor.WHITE,
                )
            }

            BasicTextField(
                value = textInput,
                onValueChange = { text ->
                    val limitedText = VolleyUiUtil.getLimitedText(maxLength, text)
                    actionToTransferContent(limitedText)
                },
                singleLine = false,
                textStyle = VolleyTypography.BodyRegular.copy(color = VolleyColor.WHITE),
                cursorBrush = SolidColor(VolleyColor.WHITE),
            )
        }
    }

    @Composable
    private fun MessageContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = VolleyDimens.DIMEN_24,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            MessageFieldGradientContainer(
                cornerRadius = cornerRadius,
                mainContainerAlignment = mainContainerAlignment,
                contentContainerAlignment = contentContainerAlignment,
                modifier = modifier,
                content = content
            )
        } else {
            MessageFieldBlurContainer(
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
    private fun MessageFieldBlurContainer(
        modifier: Modifier = Modifier,
        blurRadius: Int = VolleyDimens.DIMEN_24,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit
    ) {
        val density = LocalDensity.current
        val shape = RoundedCornerShape(cornerRadius.dp)
        var backgroundHeight: Dp by remember { mutableStateOf(0.dp) }

        Box(
            contentAlignment = mainContainerAlignment,
            modifier = modifier
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .height(backgroundHeight)
                    .fillMaxWidth()
                    .background(VolleyColor.WHITE.copy(alpha = 0.08f))
                    .clip(shape)
                    .blur(blurRadius.dp)
                    .border(
                        width = VolleyDimens.DIMEN_4.dp,
                        color = VolleyColor.WHITE.copy(alpha = 0.2f),
                        shape = shape
                    )
            )

            MessageContentBox(
                screenDensity = density,
                contentContainerAlignment = contentContainerAlignment,
                content = content,
                setBackgroundHeightCallback = { height -> backgroundHeight = height }
            )
        }
    }

    @Composable
    private fun MessageFieldGradientContainer(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        mainContainerAlignment: Alignment = Alignment.TopStart,
        contentContainerAlignment: Alignment = Alignment.TopStart,
        content: @Composable BoxScope.() -> Unit
    ) {
        val density = LocalDensity.current
        val shape = RoundedCornerShape(cornerRadius.dp)

        var backgroundHeight: Dp by remember { mutableStateOf(0.dp) }
        var containerSize by remember { mutableStateOf(IntSize.Zero) }
        var gradientRadius = VolleyUiUtil.getGradientRadiusByContainerSize(containerSize)

        Box(
            contentAlignment = mainContainerAlignment,
            modifier = modifier
                .clip(shape)
        ) {
            Box(
                modifier = Modifier
                    .onSizeChanged { size ->
                        containerSize = size
                    }
                    .height(backgroundHeight)
                    .fillMaxWidth()
                    .background(
                        shape = shape,
                        brush = Brush.radialGradient(
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_1 to VolleyColor.WHITE
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_1),
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_2 to VolleyColor.WHITE
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_2),
                            VolleyUiUtil.GRADIENT_COLOR_STOP_MARK_3 to VolleyColor.WHITE
                                .copy(alpha = VolleyUiUtil.GRADIENT_COLOR_STOP_VALUE_3),
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

            MessageContentBox(
                screenDensity = density,
                contentContainerAlignment = contentContainerAlignment,
                content = content,
                setBackgroundHeightCallback = { height -> backgroundHeight = height }
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
        content: @Composable BoxScope.() -> Unit
    ) {
        Box(
            modifier = modifier
                .onSizeChanged { size ->
                    val pxHeight = size.height
                    val dpHeight = with(screenDensity) { pxHeight.toDp() }
                    setBackgroundHeightCallback(
                        if (dpHeight.value < VolleyDimens.DIMEN_90.toFloat()) VolleyDimens.DIMEN_90.dp else dpHeight
                    )
                },
            contentAlignment = contentContainerAlignment,
            content = content
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewMessageField() {
    Root {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.SEAWAVE_BACKGROUND)
        ) {
            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_44.dp))

            VolleyMessageTextField.MessageField(
                hint = "Some hint...",
                textInput = "",
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_20.dp, 0.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_24.dp))

            VolleyMessageTextField.MessageField(
                hint = "Some hint...",
                textInput = stringResource(R.string.lorem_ipsum),
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_20.dp, 0.dp)
            ) { }
        }
    }
}
