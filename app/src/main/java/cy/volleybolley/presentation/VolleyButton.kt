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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
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
            shape = RoundedCornerShape(16.dp),
            contentPadding = paddingValues
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
    fun ActiveButtonSmallText( // такую кнопку только одну нашла: с текстом "Add payment"
        modifier: Modifier = Modifier,
        //enabled: Boolean = true,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp,8.dp,16.dp,8.dp),
        onClick: () -> Unit
    ) {
        Button(
           // enabled = enabled,
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.YellowPro//,
                //disabledContainerColor = VolleyColor.GreyDisabled
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = paddingValues
        ) {
            Text(
//                text = text.uppercase(),
//                color = if (enabled) {
//                    VolleyColor.BlackText
//                } else {
//                    VolleyColor.White
//                },
                text = text,
                color = VolleyColor.BlackText,
                fontSize = 16.sp,
                fontFamily = Hero400Font
            )
        }
    }
    @Composable
    @Stable
    fun ActiveButtonMap( // такую кнопку только одну нашла: с текстом "Map"
        modifier: Modifier = Modifier,
        //enabled: Boolean = true,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
        onClick: () -> Unit
    ) {
        Button(
            // enabled = enabled,
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.OrangeHard//,
                //disabledContainerColor = VolleyColor.GreyDisabled
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = paddingValues
        ) {
            Text(
//                text = text.uppercase(),
//                color = if (enabled) {
//                    VolleyColor.BlackText
//                } else {
//                    VolleyColor.White
//                },
                text = text,
                color = VolleyColor.BlackText,
                fontSize = 16.sp,
                fontFamily = Hero400Font
            )
        }
    }

    @Composable
    @Stable
    fun OutlinedActiveButton(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
        onClick: () -> Unit
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(1.dp, VolleyColor.YellowPro),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier,
            contentPadding = paddingValues
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
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
        onClick: () -> Unit
    ) {
        Box(
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
                ),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = text,
                color = VolleyColor.BlackText,
                fontFamily = Hero400Font,
                fontSize = 16.sp,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    @Composable
    @Stable
    fun OutlinedGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
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
            modifier = modifier,
            contentPadding = paddingValues
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
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
        onClick: () -> Unit
    ) {
         if (isChecked) {
            ActiveGradientButton(
                onClick = {
                    onClick()
                },
                modifier = modifier,
                paddingValues = paddingValues,
                text = text
            )
        } else {
            OutlinedGradientButton(
                onClick = {
                    onClick()
                },
                modifier = modifier,
                paddingValues = paddingValues,
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
        paddingValues: PaddingValues = PaddingValues(16.dp,12.dp,16.dp,12.dp),
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
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(paddingValues))
                {
                    Text(
                        text = text,
                        color = VolleyColor.BlackText,
                        fontFamily = Hero400Font,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Image(
                        painter = painterResource(R.drawable.arrow_right_black),
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
                modifier = modifier,
                contentPadding = paddingValues
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
                        painter = painterResource(R.drawable.arrow_right_white),
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
        paddingValues: PaddingValues = PaddingValues(12.dp,8.dp,12.dp,8.dp),
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
                    modifier = Modifier.padding(paddingValues))
                {
                    Image(
                        painter = iconPainter,
                        contentDescription = null
                    )
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
                contentPadding = paddingValues

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Image(
                        painter = iconPainter,
                        contentDescription = null
                    )
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
        if(isChecked) iconPainter = painterResource(R.drawable.arrow_levelup_black)
        else iconPainter = painterResource(R.drawable.arrow_levelup_gradient)

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
        if(isChecked) iconPainter = painterResource(R.drawable.arrow_leveldown_black)
        else iconPainter = painterResource(R.drawable.arrow_leveldown_gradient)

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
        if(isChecked) iconPainter = painterResource(R.drawable.mark_black)
        else iconPainter = painterResource(R.drawable.mark_gradient)

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
        modifier: Modifier = Modifier,
        isExclusive: Boolean = true, // true  - только одна кнопка в группе может быть выбрана (false - любое количество)
        onSelected: (Int) -> Unit
    ){
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
    fun GroupButtonsForChangeLevel(checkId: Int, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                    _, isChecked, onClick ->
                        ButtonLevelDown(
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 2
                override val isChecked: Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                    _, isChecked, onClick ->
                        ButtonConfirmLevel(
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 3
                override val isChecked: Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                    _, isChecked, onClick ->
                        ButtonLevelUp(
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(63.dp),
            true,
            onSelected = onSelected
            )
    }

    // группа кнопок выбора даты (из 2 кнопок: Today, Pick date)
    @Composable
    fun GroupButtonsForDate2(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Today",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 2
                override val isChecked: Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckedGradientButtonRightImage(
                            text = "Pick date",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(44.dp),
            true,
            onSelected = onSelected
        )
    }

    // группа кнопок выбора даты (из 3 кнопок: Today, Tomorrow, Pick date)
    @Composable
    fun GroupButtonsForDate3(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Today",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem {
                override val id = 2
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Tomorrow",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 3
                override val isChecked: Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton(
                            text = "Pick date",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(44.dp),
            true,
            onSelected = onSelected
        )
    }

    // группа кнопок выбора Privacy (из 2 кнопок: Public, Private)
    @Composable
    fun GroupButtonsForPrivacy(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Public",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 2
                override val isChecked: Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckedGradientButtonRightImage(
                            text = "Private",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(44.dp),
            true,
            onSelected = onSelected
        )
    }

    // группа кнопок выбора пола (из 3 кнопок: Mix, Men, Women)
    @Composable
    fun GroupButtonsForGender3(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Mix",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem {
                override val id = 2
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Men",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem{
                override val id = 3
                override val isChecked: Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton(
                            text = "Women",
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(44.dp),
            true,
            onSelected = onSelected
        )
    }

    // группа кнопок выбора пола (из 2 кнопок: Male, Female)
    @Composable
    fun GroupButtonsForGender2(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        val paddingValues = PaddingValues(10.dp)
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Male",
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem {
                override val id = 2
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Female",
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(40.dp),
            true,
            onSelected = onSelected
        )
    }

    // группа кнопок выбора Level (из 4 кнопок: Light, Medium, Hard, Pro)
    @Composable
    fun GroupButtonsForLevel(checkId: Int = 1,  isExclusive: Boolean = true, modifier: Modifier, onSelected: (Int) -> Unit)
    {
        val paddingValues = PaddingValues(10.dp)
        ButtonsGroup(listOf(
            object: ButtonItem {
                override val id = 1
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Light",
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem {
                override val id = 2
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            text = "Medium",
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem {
                override val id = 3
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            //modifier = modifier,
                            text = "Hard",
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
            },
            object: ButtonItem {
                override val id = 4
                override val isChecked : Boolean = (checkId == id)
                override val button : @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                    {
                            _, isChecked, onClick ->
                        CheckGradientButton (
                            //modifier = modifier,
                            text = "Pro",
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
            }
        ),
            modifier = modifier.height(40.dp),
            isExclusive = isExclusive,
            onSelected = onSelected
        )
    }

    // группа кнопок выбора Tourney Type (из 2 кнопок: Individual, Team)
    @Composable
    fun GroupButtonsForTourneyType(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        var selectedButton by remember { mutableIntStateOf(checkId) }

        Row(modifier= modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CheckGradientButton(
                modifier = Modifier.weight(1f),
                text = "Individual",
                isChecked = (selectedButton == 1),
                onClick = {
                    selectedButton = 1
                    onSelected(selectedButton)
                }
            )
            CheckGradientButton(
                modifier = Modifier.weight(1f),
                text = "Team",
                isChecked = (selectedButton == 2),
                onClick = {
                    selectedButton = 2
                    onSelected(selectedButton)
                }
            )
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewActiveButton() {
    Root1 {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = VolleyColor.TurquoiseDark)
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                VolleyButton.ActiveButton(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .height(44.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "ACTIVE BUTTON",
                    onClick = {}
                )
//                VolleyButton.OutlinedActiveButton(
//                    modifier = Modifier
//                        .padding(vertical = 12.dp)
//                        .height(44.dp)
//                        .align(Alignment.CenterHorizontally),
//                    text = "OUTLINED BUTTON",
//                    onClick = {}
//                )
//                VolleyButton.ActiveGradientButton(
//                    modifier = Modifier
//                        .padding(vertical = 12.dp)
//                        .height(44.dp)
//                        .align(Alignment.CenterHorizontally),
//                    text = "Gradient button",
//                    onClick = {}
//                )
//                VolleyButton.OutlinedGradientButton(
//                    modifier = Modifier
//                        .padding(vertical = 12.dp)
//                        .height(44.dp)
//                        .align(Alignment.CenterHorizontally),
//                    text = "Outlined gradient button",
//                    onClick = {}
//                )
                VolleyButton.GroupButtonsForChangeLevel(
                    1,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForDate2(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForDate3(
                    3,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForPrivacy(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForGender3(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForGender2(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForLevel(
                    2,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelected = {}
                )
                VolleyButton.GroupButtonsForTourneyType(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    onSelected = {}
                )
                VolleyButton.ActiveButtonSmallText(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .height(35.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Add payment",
                    onClick = {}
                )
                VolleyButton.ActiveButtonMap(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .height(44.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Map",
                    onClick = {}
                )
            }
        }
    }
}


