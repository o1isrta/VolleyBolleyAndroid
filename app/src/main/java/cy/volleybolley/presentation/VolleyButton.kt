package cy.volleybolley.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.R
import cy.volleybolley.Root1
import cy.volleybolley.presentation.VolleyButton.ButtonConfirmLevel
import cy.volleybolley.presentation.VolleyButton.ButtonItem
import cy.volleybolley.presentation.VolleyButton.ButtonLevelDown
import cy.volleybolley.presentation.VolleyButton.ButtonLevelUp
import cy.volleybolley.presentation.VolleyButton.CheckGradientButton


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
         if (isChecked) {
            ActiveGradientButton(
                onClick = {
                    onClick()
                },
                modifier = modifier,
                text = text
            )
        } else {
            OutlinedGradientButton(
                onClick = {
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
        onClick: () -> Unit
    ) {
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
        iconPainter: Painter,
        onClick: () -> Unit
    ) {
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
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp, 8.dp, 12.dp, 8.dp))
                {
                    Image(
                        painter = iconPainter,
                        contentDescription = null
                    )
                   // Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = text,
                        color = VolleyColor.BlackText,
                        fontFamily = Hero400Font,
                        fontSize = 14.sp
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
                modifier = modifier,
                contentPadding = PaddingValues(12.dp, 8.dp, 12.dp, 8.dp)

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = iconPainter,
                        contentDescription = null
                    )
                   // Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = text,
                        color = VolleyColor.White,
                        fontFamily = Hero400Font,
                        fontSize = 14.sp
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
        onClick: () -> Unit = {}
    ) {
        var iconPainter : Painter
        if(isChecked) iconPainter = painterResource(id = R.drawable.arrow_levelup_black)
        else iconPainter = painterResource(id = R.drawable.arrow_levelup_gradient)

        CheckedGradientButtonTopImage(
                onClick = onClick,
                modifier = modifier,
                text = "Level up",
                isChecked = isChecked,
                iconPainter = iconPainter
            )
    }

    @Composable
    @Stable
    fun ButtonLevelDown(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        var iconPainter : Painter
        if(isChecked) iconPainter = painterResource(id = R.drawable.arrow_leveldown_black)
        else iconPainter = painterResource(id = R.drawable.arrow_leveldown_gradient)


        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = "Level down",
            isChecked = isChecked,
            iconPainter = iconPainter
        )
    }

    @Composable
    @Stable
    fun ButtonConfirmLevel(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        var iconPainter : Painter
        if(isChecked) iconPainter = painterResource(id = R.drawable.mark_black)
        else iconPainter = painterResource(id = R.drawable.mark_gradient)

        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = "Confirm level",
            isChecked = isChecked,
            iconPainter = iconPainter
        )
    }

    @Stable
    interface ButtonItem{
        val id: Int                                             // порядковый номер кнопки в группе
        val isChecked: Boolean                                  // true - кнопка нажата
        val button: @Composable (modifier: Modifier, isChecked: Boolean, onClick: () -> Unit) -> Unit // кнопка
    }

    @Composable
    fun ButtonsGroup(
        items: List<ButtonItem>,     // список кнопок
        isExclusive: Boolean = true, // true  - только одна кнопка в группе может быть выбрана (false - любое количество)
        modifier: Modifier = Modifier,
        onSelected: (Int) -> Unit
    ){
        //val state = rememberSaveable { mutableStateOf<Set<Int>>(emptySet())}
        var checkSet : Set<Int> = emptySet()
        items.forEach { item ->
            if (item.isChecked){
                checkSet = checkSet + item.id
            }
        }
        val state = rememberSaveable { mutableStateOf<Set<Int>>(checkSet)}

        Row(modifier, horizontalArrangement = Arrangement.Start)
        {
            items.forEach { item ->
                val isCheck = state.value.contains(item.id)
                val onClick = {
                    if(isExclusive){ // добавить текущий элемент, удалив остальные/ удалить элемент
                        state.value =
                            if (isCheck)
                                state.value - item.id
                            else setOf(item.id)
                    } else { // добавить/удалить текущий элемент
                        state.value =
                            if (isCheck)
                                state.value - item.id
                            else
                                state.value + item.id
                    }
                    onSelected(item.id)
                }
                item.button(modifier, isCheck, onClick)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    @Composable
    fun GroupButtonsForLevel(/*checkId: Int,*/ modifier: Modifier, onSelected: (Int) -> Unit)
    {
        ButtonsGroup(listOf(
            object: ButtonItem{
                override val id = 1;
                override val isChecked: Boolean = false;
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                {
                modifier, isChecked, onClick ->
                    ButtonLevelUp(
                        modifier = modifier,
                        isChecked = isChecked,
                        onClick = onClick
                    )
                }
            },
            object: ButtonItem{
                override val id = 2;
                override val isChecked: Boolean = true;
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                    modifier, isChecked, onClick ->
                        ButtonConfirmLevel(
                            modifier = modifier,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 3;
                override val isChecked: Boolean = false;
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                    modifier, isChecked, onClick ->
                        ButtonLevelDown(
                            modifier = modifier,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            }
        ),
            true,
            modifier = modifier,
            onSelected = onSelected
            )
    }
}
//    @Composable
//    fun GroupGradientButtonsCheckAlone(buttons: List<@Composable () -> Unit>
//    ){
//        var selectedId by remember { mutableStateOf<Int?>(null) }
//
//        Row{
//            ButtonsGroup()
//        }
////        Row(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(8.dp),
////            horizontalArrangement = Arrangement.Start //Arrangement.spacedBy(8.dp)
////        ) {
////            buttons.forEach { button ->
////                button()
////            }
////        }
//    }
//
//    @Composable
//    fun GroupGradientButtonsCheckMany(
//
//    ){
//
//    }






@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewActiveButton() {
    Root1 {
        val lazyListState = rememberLazyListState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = VolleyColor.TurquoiseDark)

               // .scrollState(scrollState)
                //.verticalScroll(scrollState)
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ){
               item{ VolleyButton.ActiveButton(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                        .height(44.dp)
                        .fillMaxWidth(),
                    text = "ACTIVE BUTTON",
                    onClick = {}
                )}
                item{ VolleyButton.OutlinedActiveButton(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                        .height(44.dp)
                        .fillMaxWidth(),
                    text = "OUTLINED BUTTON",
                    onClick = {}
                )}
                item{ VolleyButton.ActiveGradientButton(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                        .height(44.dp)
                        .fillMaxWidth(),
                    text = "Gradient button",
                    onClick = {}
                )}
                item{VolleyButton.GroupButtonsForLevel(
                    //2,
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                        .height(63.dp)
                        .fillMaxWidth(),
                    onSelected = {}
                )}
                item {
                    VolleyButton.ButtonsGroup(
                        listOf(
                        object : ButtonItem {
                            override val id = 1;
                            override val isChecked = true;
                            override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                                { modifier, isChecked, onClick ->
                                    CheckGradientButton(
                                        modifier = modifier,
                                        text = "Light",
                                        isChecked = isChecked,
                                        onClick = onClick
                                    )
                                }
                        },
                        object : ButtonItem {
                            override val id = 2;
                            override val isChecked = true;
                            override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                                { modifier, isChecked, onClick ->
                                    CheckGradientButton(
                                        modifier = modifier,
                                        text = "Medium",
                                        isChecked = isChecked,
                                        onClick = onClick
                                    )
                                }
                        },
                        object : ButtonItem {
                            override val id = 3;
                            override val isChecked = true;
                            override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                                { modifier, isChecked, onClick ->
                                    CheckGradientButton(
                                        modifier = modifier,
                                        text = "High",
                                        isChecked = isChecked,
                                        onClick = onClick
                                    )
                                }
                        },
                            object : ButtonItem {
                                override val id = 4;
                                override val isChecked = false;
                                override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                                    { modifier, isChecked, onClick ->
                                        CheckGradientButton(
                                            modifier = modifier,
                                            text = "Pro",
                                            isChecked = isChecked,
                                            onClick = onClick
                                        )
                                    }
                            }
                    ),
                        false,
                        modifier = Modifier
                            .padding(it)
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        onSelected = {}
                    )
                }

//                item{ VolleyButton.OutlinedGradientButton(
//                    modifier = Modifier
//                        .padding(it)
//                        .padding(horizontal = 24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "Outlined gradient button",
//                    onClick = {}
//                )}
//                item{ VolleyButton.CheckGradientButton(
//                    modifier = Modifier
//                        .padding(it)
//                        .padding(horizontal = 24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "CheckGradientButton. Checked = true",
//                    onClick = {},
//                    isChecked = true
//                )}
//                item{ VolleyButton.CheckGradientButton(
//                    modifier = Modifier
//                        .padding(it)
//                        .padding(horizontal = 24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "CheckGradientButton. Checked = false",
//                    onClick = {},
//                    isChecked = false
//                )}
//                item{ VolleyButton.CheckedGradientButtonRightImage(
//                    modifier = Modifier
//                        .padding(it)
//                        .padding(horizontal = 24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "isChecked = true",
//                    onClick = {},
//                    isChecked = true
//                )}
//                item{ VolleyButton.CheckedGradientButtonRightImage(
//                    modifier = Modifier
//                        .padding(it)
//                        .padding(horizontal = 24.dp)
//                        .height(44.dp)
//                        .fillMaxWidth(),
//                    text = "isChecked = false",
//                    onClick = {},
//                    isChecked = false
//                )}
//                item{ VolleyButton.CheckedGradientButtonTopImage(
//                    modifier = Modifier
//                        .padding(it)
//                        .padding(horizontal = 24.dp)
//                        .height(63.dp)
//                        .fillMaxWidth(),
//                    text = "isChecked = true",
//                    onClick = {},
//                    isChecked = true,
//                    iconPainter = painterResource(id = R.drawable.mark_black)
//                    //iconResId = R.drawable.mark_black
//                )}
                item{ VolleyButton.CheckedGradientButtonTopImage(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                        .height(63.dp)
                        .fillMaxWidth(),
                    text = "isChecked = false",
                    onClick = {},
                    isChecked = false,
                    iconPainter = painterResource(id = R.drawable.mark_gradient)
                    //iconResId = R.drawable.mark_gradient
                )}
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


