package cy.volleybolley.core.presentation.ui.component

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.Root1
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ACTIVE_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ADD_PAYMENT_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.CHECK_GRADIENT_BUTTON_FALSE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.CHECK_GRADIENT_BUTTON_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_FALSE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.LEVEL_UP_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.MAP_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonSText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonXSText

@UiLibraryMarker
object VolleyButton {
    const val LEVEL_UP_TEXT = "Level up"
    const val LEVEL_DOWN_TEXT = "Level down"
    const val CONFIRM_LEVEL_TEXT = "Confirm level"
    const val TODAY_TEXT = "Today"
    const val TOMORROW_TEXT = "Tomorrow"
    const val PICK_DATE_TEXT = "Pick date"
    const val PUBLIC_TEXT = "Public"
    const val PRIVATE_TEXT = "Private"
    const val MIX_TEXT = "Mix"
    const val MEN_TEXT = "Men"
    const val WOMEN_TEXT = "Women"
    const val MALE_TEXT = "Male"
    const val FEMALE_TEXT = "Female"
    const val LIGHT_TEXT = "Light"
    const val MEDIUM_TEXT = "Medium"
    const val HARD_TEXT = "Hard"
    const val PRO_TEXT = "Pro"
    const val INDIVIDUAL_TEXT = "Individual"
    const val TEAM_TEXT = "Team"
    const val ACTIVE_BUTTON_TEXT ="ACTIVE BUTTON"
    const val OUTLINED_BUTTON_TEXT ="OUTLINED BUTTON"
    const val ADD_PAYMENT_TEXT = "Add payment"
    const val MAP_TEXT = "Map"
    const val GRADIENT_BUTTON_TEXT = "Gradient button"
    const val OUTLINED_GRADIENT_BUTTON_TEXT = "Outlined gradient button"
    const val CHECK_GRADIENT_BUTTON_TRUE_TEXT = "Check gradient button, isChecked = true"
    const val CHECK_GRADIENT_BUTTON_FALSE_TEXT = "Check gradient button, isChecked = false"
    const val ISCHECKED_TRUE_TEXT = "isChecked = true"
    const val ISCHECKED_FALSE_TEXT = "isChecked = false"


    @Composable
    @Stable
    fun ActiveButton(
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
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
                    VolleyColor.TEXT_DARK
                } else {
                    VolleyColor.White
                },
                style = ButtonText
            )
        }
    }

    @Composable
    @Stable
    fun OutlinedActiveButton(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
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
                style = ButtonText
            )
        }
    }

    /**
     * такую кнопку только одну нашла: с текстом "Map"
     */
    @Composable
    @Stable
    fun ActiveButtonMap(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
        onClick: () -> Unit
    ) {
        Button(
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.OrangeHard
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = paddingValues
        ) {
            Text(
                text = text,
                color = VolleyColor.TEXT_DARK,
                style = ButtonSText
            )
        }
    }

    /**
     * градиентная кнопка без картинки
     */
    @Composable
    @Stable
    fun ActiveGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
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
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = 100f)
                    ),
                    shape = RoundedCornerShape(size = 16.dp),
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = VolleyColor.TEXT_DARK,
                style = ButtonSText,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

    @Composable
    @Stable
    fun OutlinedGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
        onClick: () -> Unit
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = 100f)
                )
            ),
            shape = RoundedCornerShape(size = 16.dp),
            modifier = modifier,
            contentPadding = paddingValues
        ) {
            Text(
                text = text,
                color = VolleyColor.White,
                style = ButtonSText
            )
        }
    }

    /**
     * такую кнопку только одну нашла: с текстом "Add payment"
     */
    @Composable
    @Stable
    fun OutlinedActiveButtonSmallText(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp, 8.dp, 16.dp, 8.dp),
        onClick: () -> Unit
    ) {
        OutlinedGradientButton(
            modifier = modifier,
            text = text,
            paddingValues = paddingValues,
            onClick = onClick
        )
    }

    /**
     * @param isChecked кнопка не выбрана
     */
    @Composable
    @Stable
    fun CheckGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
        onClick: () -> Unit
    ) {
        if (isChecked) {
            ActiveGradientButton(
                onClick = onClick,
                modifier = modifier,
                paddingValues = paddingValues,
                text = text
            )
        } else {
            OutlinedGradientButton(
                onClick = onClick,
                modifier = modifier,
                paddingValues = paddingValues,
                text = text
            )
        }
    }

    /**
     * с картинкой справа
     */
    @Composable
    @Stable
    fun CheckedGradientButtonRightImage(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
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
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = 100f)
                        ),
                        shape = RoundedCornerShape(size = 16.dp),
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Text(
                        text = text,
                        color = VolleyColor.TEXT_DARK,
                        style = ButtonSText
                    )
                    Spacer(modifier = Modifier.size(size = 8.dp))
                    Image(
                        painter = painterResource(R.drawable.arrow_right_black),
                        contentDescription = null,
                        modifier = Modifier.size(width = 17.dp, height = 16.dp)
                    )
                }
            }
        } else {
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            VolleyColor.YellowForGradient,
                            VolleyColor.GreenForGradient
                        ),
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = 100f)
                    )
                ),
                shape = RoundedCornerShape(size = 16.dp),
                modifier = modifier,
                contentPadding = paddingValues
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = text,
                        color = VolleyColor.White,
                        style = ButtonSText
                    )
                    Spacer(modifier = Modifier.size(size = 8.dp))
                    Image(
                        painter = painterResource(R.drawable.arrow_right_white),
                        contentDescription = null,
                        modifier = Modifier.size(width = 17.dp, height = 16.dp)
                    )
                }
            }
        }
    }

    /**
     * с картинкой наверху, вспомогательная функция для кнопок LevelUp, LevelDown, ConfirmLevel
     */
    @Composable
    @Stable
    fun CheckedGradientButtonTopImage(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,
        iconPainter: Painter,
        paddingValues: PaddingValues = PaddingValues(12.dp, 8.dp, 12.dp, 8.dp),
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
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = 100f)
                        ),
                        shape = RoundedCornerShape(size = 16.dp),
                    )

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Image(
                        painter = iconPainter,
                        contentDescription = null,
                        modifier = modifier.size(width = 57.dp, height = 30.dp)
                    )
                    Text(
                        text = text,
                        color = VolleyColor.TEXT_DARK,
                        style = ButtonXSText,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            OutlinedButton(
                onClick = onClick,
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            VolleyColor.YellowForGradient,
                            VolleyColor.GreenForGradient
                        ),
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = 0f, y = 100f)
                    )
                ),
                shape = RoundedCornerShape(size = 16.dp),
                modifier = modifier,
                contentPadding = paddingValues
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = iconPainter,
                        contentDescription = null,
                        modifier = modifier.size(width = 57.dp, height = 30.dp)
                    )
                    Text(
                        text = text,
                        color = VolleyColor.White,
                        style = ButtonXSText
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
        val iconPainter: Painter = if (isChecked) {
            painterResource(R.drawable.arrow_levelup_black)
        } else {
            painterResource(R.drawable.arrow_levelup_gradient)
        }

        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = LEVEL_UP_TEXT,
            isChecked = isChecked,
            iconPainter = iconPainter
        )
    }

    @Composable
    @Stable
    fun ButtonLevelDown(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit = {}
    ) {
        val iconPainter = if (isChecked) {
            painterResource(R.drawable.arrow_leveldown_black)
        } else {
            painterResource(R.drawable.arrow_leveldown_gradient)
        }

        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = LEVEL_DOWN_TEXT,
            isChecked = isChecked,
            iconPainter = iconPainter
        )
    }

    @Composable
    @Stable
    fun ButtonConfirmLevel(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit = {}
    ) {
        val iconPainter = if (isChecked) {
            painterResource(R.drawable.mark_black)
        } else {
            painterResource(R.drawable.mark_gradient)
        }

        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = CONFIRM_LEVEL_TEXT,
            isChecked = isChecked,
            iconPainter = iconPainter
        )
    }

    @Stable
    interface ButtonItem {
        /**
         * порядковый номер кнопки в группе
         */
        val position: Int

        /**
         * true - кнопка нажата
         */
        val isChecked: Boolean
        val button: @Composable (modifier: Modifier, isChecked: Boolean, onClick: () -> Unit) -> Unit // кнопка
    }

    /**
     * @param items список кнопок
     * @param isExclusive true  - только одна кнопка в группе может быть выбрана (false - любое количество)
     */
    @Composable
    fun ButtonsGroup(
        items: List<ButtonItem>,
        modifier: Modifier = Modifier,
        isExclusive: Boolean = true,
        onSelected: (Int) -> Unit
    ) {
        var checkSet = emptySet<Int>()
        items.forEach { item ->
            if (item.isChecked) {
                checkSet = checkSet + item.position
            }
        }
        val state = rememberSaveable { mutableStateOf(checkSet) }

        Row(modifier, horizontalArrangement = Arrangement.Start) {
            items.forEach { item ->
                val isCheck = state.value.contains(item.position)
                item.button(modifier, isCheck) {
                    if (isExclusive) { // добавить текущий элемент, удалив остальные/ удалить элемент
                        state.value = if (isCheck) {
                            state.value - item.position
                        } else {
                            setOf(item.position)
                        }
                    } else { // добавить/удалить текущий элемент
                        state.value = if (isCheck) {
                            state.value - item.position
                        } else {
                            state.value + item.position
                        }
                    }
                    onSelected(item.position)
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    @Composable
    fun GroupButtonsForChangeLevel(checkId: Int, modifier: Modifier, onSelected: (Int) -> Unit) {
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            ButtonLevelDown(
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            ButtonConfirmLevel(
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 3
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            ButtonLevelUp(
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                }
            ),
            modifier = modifier.height(height = 63.dp),
            isExclusive = true,
            onSelected = onSelected
        )
    }

    /**
     * группа кнопок выбора даты (из 2 кнопок: Today, Pick date)
     */
    @Composable
    fun GroupButtonsForDate2(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = TODAY_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckedGradientButtonRightImage(
                                text = PICK_DATE_TEXT,
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

    /**
     * группа кнопок выбора даты (из 3 кнопок: Today, Tomorrow, Pick date)
     */
    @Composable
    fun GroupButtonsForDate3(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = TODAY_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = TOMORROW_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 3
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = PICK_DATE_TEXT,
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

    /**
     * группа кнопок выбора Privacy (из 2 кнопок: Public, Private)
     */
    @Composable
    fun GroupButtonsForPrivacy(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = PUBLIC_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckedGradientButtonRightImage(
                                text = PRIVATE_TEXT,
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

    /**
     * группа кнопок выбора пола (из 3 кнопок: Mix, Men, Women)
     */
    @Composable
    fun GroupButtonsForGender3(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = MIX_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = MEN_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 3
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = WOMEN_TEXT,
                                isChecked = isChecked,
                                onClick = onClick
                            )
                        }
                }
            ),
            modifier = modifier.height(44.dp),
            isExclusive = true,
            onSelected = onSelected
        )
    }

    /**
     * группа кнопок выбора пола (из 2 кнопок: Male, Female)
     */
    @Composable
    fun GroupButtonsForGender2(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        val paddingValues = PaddingValues(10.dp)
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = MALE_TEXT,
                                isChecked = isChecked,
                                paddingValues = paddingValues,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = FEMALE_TEXT,
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

    /**
     * группа кнопок выбора Level (из 4 кнопок: Light, Medium, Hard, Pro)
     */
    @Composable
    fun GroupButtonsForLevel(
        checkId: Int = 1,
        isExclusive: Boolean = true,
        modifier: Modifier,
        onSelected: (Int) -> Unit
    ) {
        val paddingValues = PaddingValues(all = 10.dp)
        ButtonsGroup(
            listOf(
                object : ButtonItem {
                    override val position = 1
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = LIGHT_TEXT,
                                isChecked = isChecked,
                                paddingValues = paddingValues,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 2
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = MEDIUM_TEXT,
                                isChecked = isChecked,
                                paddingValues = paddingValues,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 3
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = HARD_TEXT,
                                isChecked = isChecked,
                                paddingValues = paddingValues,
                                onClick = onClick
                            )
                        }
                },
                object : ButtonItem {
                    override val position = 4
                    override val isChecked = checkId == position
                    override val button: @Composable (Modifier, Boolean, () -> Unit) -> Unit =
                        { _, isChecked, onClick ->
                            CheckGradientButton(
                                text = PRO_TEXT,
                                isChecked = isChecked,
                                paddingValues = paddingValues,
                                onClick = onClick
                            )
                        }
                }
            ),
            modifier = modifier.height(height = 40.dp),
            isExclusive = isExclusive,
            onSelected = onSelected
        )
    }

    /**
     * группа кнопок выбора Tourney Type (из 2 кнопок: Individual, Team)
     */
    @Composable
    fun GroupButtonsForTourneyType(checkId: Int = 1, modifier: Modifier, onSelected: (Int) -> Unit) {
        var selectedButton by remember { mutableIntStateOf(checkId) }

        Row(
            modifier = modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CheckGradientButton(
                modifier = Modifier.weight(1f),
                text = INDIVIDUAL_TEXT,
                isChecked = selectedButton == 1,
                onClick = {
                    selectedButton = 1
                    onSelected(selectedButton)
                }
            )
            CheckGradientButton(
                modifier = Modifier.weight(1f),
                text = TEAM_TEXT,
                isChecked = selectedButton == 2,
                onClick = {
                    selectedButton = 2
                    onSelected(selectedButton)
                }
            )
        }
    }
}

@Composable
@Stable
private fun PreviewContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(color = VolleyColor.TurquoiseDark)
            .width(250.dp)
            .height(120.dp)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewActiveButton() {
    PreviewContainer {
        VolleyButton.ActiveButton(
            modifier = Modifier.height(44.dp),
            text = ACTIVE_BUTTON_TEXT,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOutlinedActiveButton() {
    PreviewContainer {
        VolleyButton.OutlinedActiveButton(
            modifier = Modifier.height(44.dp),
            text = OUTLINED_BUTTON_TEXT,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOutlinedActiveButtonSmallText() {
    PreviewContainer {
        VolleyButton.OutlinedActiveButtonSmallText(
            modifier = Modifier.height(35.dp),
            text = ADD_PAYMENT_TEXT,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewActiveButtonMap() {
    PreviewContainer {
        VolleyButton.ActiveButtonMap(
            modifier = Modifier.height(44.dp),
            text = MAP_TEXT,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewActiveGradientButton() {
    PreviewContainer {
        VolleyButton.ActiveGradientButton(
            modifier = Modifier.height(44.dp),
            text = GRADIENT_BUTTON_TEXT,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOutlinedGradientButton() {
    PreviewContainer(modifier = Modifier.width(300.dp)) {
        VolleyButton.OutlinedGradientButton(
            modifier = Modifier.height(44.dp),
            text = OUTLINED_GRADIENT_BUTTON_TEXT,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCheckGradientButton() {
    PreviewContainer(
        modifier = Modifier
            .height(250.dp)
            .width(300.dp)
    ) {
        Column {
            VolleyButton.CheckGradientButton(
                modifier = Modifier
                    .padding(24.dp)
                    .height(44.dp),
                isChecked = true,
                text = CHECK_GRADIENT_BUTTON_TRUE_TEXT,
                onClick = {}
            )
            VolleyButton.CheckGradientButton(
                modifier = Modifier
                    .padding(24.dp)
                    .height(44.dp),
                isChecked = false,
                text = CHECK_GRADIENT_BUTTON_FALSE_TEXT,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCheckedGradientButtonRightImage() {
    PreviewContainer(
        modifier = Modifier
            .height(250.dp)
            .width(300.dp)
    ) {
        Column {
            VolleyButton.CheckedGradientButtonRightImage(
                modifier = Modifier
                    .padding(24.dp)
                    .height(44.dp),
                isChecked = true,
                text = ISCHECKED_TRUE_TEXT,
                onClick = {}
            )
            VolleyButton.CheckGradientButton(
                modifier = Modifier
                    .padding(24.dp)
                    .height(44.dp),
                isChecked = false,
                text = ISCHECKED_FALSE_TEXT,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCheckedGradientButtonTopImage() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.CheckedGradientButtonTopImage(
                modifier = Modifier,
                isChecked = true,
                text = LEVEL_UP_TEXT,
                iconPainter = painterResource(R.drawable.arrow_levelup_black),
                onClick = {}
            )
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.CheckedGradientButtonTopImage(
                modifier = Modifier,
                isChecked = false,
                text = LEVEL_UP_TEXT,
                iconPainter = painterResource(R.drawable.arrow_levelup_gradient),
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonLevelUp() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.ButtonLevelUp()
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.ButtonLevelUp(isChecked = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonLevelDown() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.ButtonLevelDown()
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.ButtonLevelDown(isChecked = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonConfirmLevel() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.ButtonConfirmLevel()
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.ButtonConfirmLevel(isChecked = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForChangeLevel() {
    PreviewContainer(
        modifier = Modifier
            .width(400.dp)
            .height(200.dp)
    ) {
        VolleyButton.GroupButtonsForChangeLevel(
            checkId = 1,
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForDate2() {
    PreviewContainer {
        VolleyButton.GroupButtonsForDate2(
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForDate3() {
    PreviewContainer(modifier = Modifier.width(400.dp)) {
        VolleyButton.GroupButtonsForDate3(
            checkId = 3,
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForPrivacy() {
    PreviewContainer {
        VolleyButton.GroupButtonsForPrivacy(
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForGender3() {
    PreviewContainer(modifier = Modifier.width(300.dp)) {
        VolleyButton.GroupButtonsForGender3(
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForGender2() {
    PreviewContainer {
        VolleyButton.GroupButtonsForGender2(
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForLevel() {
    PreviewContainer(modifier = Modifier.width(400.dp)) {
        VolleyButton.GroupButtonsForLevel(
            checkId = 2,
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupButtonsForTourneyType() {
    PreviewContainer {
        VolleyButton.GroupButtonsForTourneyType(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewCombo() {
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
                    text = ACTIVE_BUTTON_TEXT,
                    onClick = {}
                )
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
                    checkId = 3,
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
                VolleyButton.OutlinedActiveButtonSmallText(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .height(35.dp)
                        .align(Alignment.CenterHorizontally),
                    text = ADD_PAYMENT_TEXT,
                    onClick = {}
                )
                VolleyButton.ActiveButtonMap(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .height(44.dp)
                        .align(Alignment.CenterHorizontally),
                    text = MAP_TEXT,
                    onClick = {}
                )
            }
        }
    }
}
