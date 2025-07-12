package cy.volleybolley.presentation

import android.provider.DocumentsContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawOutline
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

/*class IconPosition {
    companion object {
        val POSITION_TOP = "top"
        val POSITION_RIGHT = "right"
        val POSITION_NO = "no"
    }
}*/

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
    @Stable
    fun ActiveButton(
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        text: String,
        onClick: () -> Unit
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
    @Stable
    fun OutlinedActiveButton(
        modifier: Modifier = Modifier,
        text: String,
        onClick: () -> Unit
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
    @Stable
    fun ActiveGradientButton(                     // градиентная кнопка без картинки
        modifier: Modifier = Modifier,
        text: String,
        onClick: () -> Unit
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clickable(onClick = onClick)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            VolleyColor.YellowForGradient,
                            VolleyColor.GreenForGradient
                        ),
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

    @Composable
    @Stable
    fun OutlinedGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        onClick: () -> Unit
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(
                1.dp, brush = Brush.linearGradient(
                    colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                    start = Offset(0f, 0f),
                    end = Offset(0f, 100f)
                )
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
        ) {
            Text(
                text = text,
                color = VolleyColor.White,
                fontFamily = Hero400Font,
                fontSize = 16.sp
            )
        }
    }

    @Composable
    @Stable
    fun CheckGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,               // кнопка не выбрана
        onClick: () -> Unit
    ) {
       // var _isChecked by remember { mutableStateOf(isChecked) }

        if (isChecked) {
            ActiveGradientButton(
                onClick = {
                    //_isChecked = !_isChecked
                    onClick()
                },
                modifier = modifier,
                text = text
            )
        } else {
            OutlinedGradientButton(
                onClick = {
                    //_isChecked = !_isChecked
                    onClick()
                },
                modifier = modifier,
                text = text
            )
        }
    }

    @Composable
    @Stable
    fun CheckedGradientButtonRightImage(                // с картинкой справа
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,
                // iconResId: Int//? = null,                     // идентификатор иконки, null - иконки нет (по умолчанию)
        onClick: () -> Unit
    ) {
       // var _isChecked by remember { mutableStateOf(isChecked) }
        if (isChecked) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .clickable(onClick = onClick)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                VolleyColor.YellowForGradient,
                                VolleyColor.GreenForGradient
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(0f, 100f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Text(
                        text = text,
                        color = VolleyColor.BlackText,
                        fontFamily = Hero400Font,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right_black),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else {
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(
                    1.dp, brush = Brush.linearGradient(
                        colors = listOf(
                            VolleyColor.YellowForGradient,
                            VolleyColor.GreenForGradient
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 100f)
                    )
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = modifier
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = text,
                        color = VolleyColor.White,
                        fontFamily = Hero400Font,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right_white),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }

    @Composable
    @Stable
    fun CheckedGradientButtonTopImage(                // с картинкой наверху, вспомогательная функция для кнопок LevelUp, LevelDown, ConfirmLevel
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,
        iconResId: Int,
        onClick: () -> Unit
    ) {
        //var _isChecked by remember { mutableStateOf(isChecked) }
        if (isChecked) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .clickable(onClick = onClick)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                VolleyColor.YellowForGradient,
                                VolleyColor.GreenForGradient
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(0f, 100f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                    )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = text,
                        color = VolleyColor.BlackText,
                        fontFamily = Hero400Font,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(
                    1.dp, brush = Brush.linearGradient(
                        colors = listOf(
                            VolleyColor.YellowForGradient,
                            VolleyColor.GreenForGradient
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 100f)
                    )
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = modifier
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = text,
                        color = VolleyColor.White,
                        fontFamily = Hero400Font,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

    @Composable
    @Stable
    fun ButtonLevelUp(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        var iconResId : Int
        if(isChecked) iconResId = R.drawable.arrow_levelup_black
        else iconResId = R.drawable.arrow_levelup_gradient

        CheckedGradientButtonTopImage(
                onClick = onClick,
                modifier = modifier,
                text = "Level up",
                isChecked = isChecked,
                iconResId =iconResId
            )
    }

    @Composable
    @Stable
    fun ButtonLevelDown(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        var iconResId : Int
        if(isChecked) iconResId = R.drawable.arrow_levelup_black
        else iconResId = R.drawable.arrow_leveldown_gradient

        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = "Level down",
            isChecked = isChecked,
            iconResId =iconResId
        )
    }

    @Composable
    @Stable
    fun ButtonConfirmLevel(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        var iconResId : Int
        if(isChecked) iconResId = R.drawable.mark_black
        else iconResId = R.drawable.mark_gradient

        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = "Confirm level",
            isChecked = isChecked,
            iconResId = iconResId
        )
    }
}
/*    @Composable
    fun GroupGradientButtonsCheckAlone(buttons: List<@Composable () -> Unit>
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start //Arrangement.spacedBy(8.dp)
        ) {
            buttons.forEach { button ->
                button()
            }
        }
    }

    @Composable
    fun GroupGradientButtonsCheckMany(

    ){

    }

    @Composable
    fun GroupButtonsForLevel(

    ){*/

            /*   {
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
                           *//* Image(
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
                            )*//*
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
        }*/



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewActiveButton() {
    Root {
       // val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = VolleyColor.TurquoiseDark)

               // .scrollState(scrollState)
                //.verticalScroll(scrollState)
        ) {
            Column {//(modifier = Modifier.verticalScroll(scrollState).fillMaxSize()){
               VolleyButton.ActiveButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "ACTIVE BUTTON",
                    onClick = {}
                )
                VolleyButton.OutlinedActiveButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "OUTLINED BUTTON",
                    onClick = {}
                )
               /* VolleyButton.ActiveGradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "Gradient button",
                    onClick = {}
                )
                VolleyButton.OutlinedGradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "Outlined gradient button",
                    onClick = {}
                )*/
                VolleyButton.CheckGradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "CheckGradientButton. Checked = true",
                    onClick = {},
                    isChecked = true
                )
                VolleyButton.CheckGradientButton(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "CheckGradientButton. Checked = false",
                    onClick = {},
                    isChecked = false
                )
                VolleyButton.CheckedGradientButtonRightImage(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "isChecked = true",
                    onClick = {},
                    isChecked = true
                )
                VolleyButton.CheckedGradientButtonRightImage(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(44.dp)
                        .fillMaxWidth(),
                    text = "isChecked = false",
                    onClick = {},
                    isChecked = false
                )
                VolleyButton.CheckedGradientButtonTopImage(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(63.dp)
                        .fillMaxWidth(),
                    text = "isChecked = true",
                    onClick = {},
                    isChecked = true,
                    iconResId = R.drawable.mark_black
                )
                VolleyButton.CheckedGradientButtonTopImage(
                    modifier = Modifier.padding(it).padding(horizontal = 24.dp).height(63.dp)
                        .fillMaxWidth(),
                    text = "isChecked = false",
                    onClick = {},
                    isChecked = false,
                    iconResId = R.drawable.mark_gradient
                )
            }




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


