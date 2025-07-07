package cy.volleybolley.presentation

import android.provider.DocumentsContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.Root

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cy.volleybolley.R

class IconPosition {
    companion object {
        val POSITION_TOP = "top"
        val POSITION_RIGHT = "right"
        val POSITION_NO = "no"
    }
}

val ActayWide700Font = FontFamily(
    Font(R.font.actay_wide_bold)
)

val Hero400Font = FontFamily(
    Font(R.font.hero_regular)
)

val Hero700Font = FontFamily(
    Font(R.font.hero_bold)
)

object VolleyButton {
    @Composable
    fun ActiveButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        text: String
    ) {
        Button(
            enabled = enabled,
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.YellowPro,
                disabledContainerColor = VolleyColor.GreyDisabled
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = text.uppercase(),
                color = if (enabled) {
                    VolleyColor.BlackText
                } else {
                    VolleyColor.White
                },
                fontSize = 16.sp,
                fontFamily = ActayWide700Font
            )
        }
    }

    @Composable
    fun OutlinedActiveButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        text: String
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(1.dp, VolleyColor.YellowPro),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
        ) {
            Text(
                text = text.uppercase(),
                color = VolleyColor.White,
                fontSize = 16.sp,
                fontFamily = ActayWide700Font
            )
        }
    }

    @Composable
    fun GradientButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        text: String,
        iconResId: Int? = null,                             // идентификатор иконки, null - иконки нет (по умолчанию)
        iconPosition: String = IconPosition.POSITION_NO     // положение иконки на кнопки относительно текста: "top" - над текстом, "right" - справа, "no" - нет иконки (по умолчанию)
        ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clickable(onClick = onClick)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 100f)
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
        )
        {
            when(iconPosition) {
                "no" -> {}

                "top" -> {
                    if (iconResId != null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Image(
                                painter = painterResource(id = iconResId),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),

                                //colorFilter = ColorFilter.colorMatrix(Brush.linearGradient(listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient))
                            )
                           // Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = text,
                                color = VolleyColor.BlackText,
                                fontFamily = Hero400Font,
                                fontSize = 16.sp
                            )
                            // возможно, нужен отступ
                            Image(
                                painter = painterResource(id = iconResId),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                                      .graphicsLayer(alpha = 0f)
                                    .drawWithCache {
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(brush = Brush.linearGradient(
                                                colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, 100f)
                                            ), blendMode = BlendMode.SrcAtop)
                                        }
                                    },
                                )
                           /* Image(
                                painter = painterResource(id = iconResId),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                                    .graphicsLayer(alpha = 0.99f)
                                    .drawWithContent {

                                            drawContent()
                                            drawRect(brush = Brush.linearGradient(
                                                colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, 100f)
                                            ), blendMode = BlendMode.SrcAtop)
                                        }
,
                            )*/
                        }
                        return
                    }
                }
                "right" -> {
                    if (iconResId != null) {
                        Row(verticalAlignment = Alignment.CenterVertically)
                        {
                            Text(
                                text = text,
                                color = VolleyColor.BlackText,
                                fontFamily = Hero400Font,
                                fontSize = 16.sp
                            )
                            // возможно, нужен отступ
                            Image(
                                painter = painterResource(id = iconResId),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        return
                    }
                }
            }
            Text(
                text = text,
                color = VolleyColor.BlackText,
                fontFamily = Hero400Font,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun CheckGradientButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = true                 // кнопка выбрана
    ) {
        Box(
            modifier = modifier
                .clickable(onClick = onClick)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 100f)
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
        )
        {
            Text(
                text = text,
                color = VolleyColor.BlackText,
                fontFamily = Hero400Font,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewActiveButton() {
    Root {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleyButton.ActiveButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
                    text = "ACTIVE BUTTON",
                    onClick = {}
                )
                VolleyButton.OutlinedActiveButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
                    text = "OUTLINED BUTTON",
                    onClick = {}
                )
                VolleyButton.GradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
                    text = "Gradient button",
                    onClick = {}
                )
                VolleyButton.GradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
                    text = "Gradient button",
                    onClick = {},
                    iconResId = R.drawable.mark_black,
                    iconPosition = IconPosition.POSITION_TOP
                )
                VolleyButton.GradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
                    text = "Gradient button",
                    onClick = {},
                    iconResId = R.drawable.arrow_right_black,
                    iconPosition = IconPosition.POSITION_RIGHT
                )
               /* Image(
                    painter = painterResource(id = R.drawable.mark_black),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithContent {
                            drawContent()
                            drawRect(brush = Brush.linearGradient(
                                colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                                start = Offset(0f, 0f),
                                end = Offset(0f, 100f)
                            ), blendMode = BlendMode.SrcAtop)
                        }
                )*/
            }
        }
    }
}

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewLoadingActiveButton() {
    Root {
        VolleyButton.ActiveButton(
            modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
            text = "Next step",
            //isLoading = true,
            onClick = {}
        )
    }
}*/
