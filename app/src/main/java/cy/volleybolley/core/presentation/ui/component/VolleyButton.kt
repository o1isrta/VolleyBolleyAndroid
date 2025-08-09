package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ACTIVE_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ADD_PAYMENT_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_FALSE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.LEVEL_UP_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.MAP_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.SliderButtonsMap
import cy.volleybolley.core.presentation.ui.component.VolleyButton.SliderButtonsPlayers
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
    const val ACTIVE_BUTTON_TEXT = "ACTIVE BUTTON"
    const val OUTLINED_BUTTON_TEXT = "OUTLINED BUTTON"
    const val ADD_PAYMENT_TEXT = "Add payment"
    const val MAP_TEXT = "Map"
    const val LIST_TEXT = "List"
    const val GRADIENT_BUTTON_TEXT = "Gradient button"
    const val OUTLINED_GRADIENT_BUTTON_TEXT = "Outlined gradient button"
    const val ISCHECKED_TRUE_TEXT = "isChecked = true"
    const val ISCHECKED_FALSE_TEXT = "isChecked = false"
    const val ALL_PLAYERS_TEXT = "All players"
    const val FAVORITES_TEXT = "Favorites"

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
                    VolleyColor.TextDark
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
                color = VolleyColor.TextDark,
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
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = VolleyColor.TextDark
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(size = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                VolleyColor.YellowForGradient,
                                VolleyColor.GreenForGradient
                            ),
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = 100f)
                        )
                    )
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = VolleyColor.TextDark,
                    style = ButtonSText
                )
            }
        }
    }

    /**
     * outlined градиентная кнопка без картинки
     */
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
        onClick: () -> Unit
    ) {
        OutlinedGradientButton(
            modifier = modifier,
            text = text,
            paddingValues = PaddingValues(16.dp, 8.dp, 16.dp, 8.dp),
            onClick = onClick
        )
    }

    /**
     * используется для групп кнопок
     * @param isChecked кнопка выбрана/не выбрана
     */
    @Composable
    @Stable
    fun CheckGradientButton(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean,
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
            Button(
                onClick = onClick,
                modifier = modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = VolleyColor.TextDark
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    VolleyColor.YellowForGradient,
                                    VolleyColor.GreenForGradient
                                ),
                                start = Offset(x = 0f, y = 0f),
                                end = Offset(x = 0f, y = 100f)
                            )
                        )
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = text,
                            color = VolleyColor.TextDark,
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
            Button(
                onClick = onClick,
                modifier = modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = VolleyColor.TextDark
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    VolleyColor.YellowForGradient,
                                    VolleyColor.GreenForGradient
                                ),
                                start = Offset(x = 0f, y = 0f),
                                end = Offset(x = 0f, y = 100f)
                            )
                        )
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = iconPainter,
                            contentDescription = null,
                            modifier = modifier.size(width = 57.dp, height = 30.dp)
                        )
                        Text(
                            text = text,
                            color = VolleyColor.TextDark,
                            style = ButtonXSText,
                            fontSize = 14.sp
                        )
                    }
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

    @Immutable
    @Stable
    data class ButtonItem(
        /**
         * порядковый номер кнопки в группе
         */
        val position: Int,
        /**
         * true - кнопка нажата
         */
        val isChecked: Boolean,
        val button: @Composable (modifier: Modifier, isChecked: Boolean, onClick: () -> Unit) -> Unit // кнопка
    )

    /**
     * @param items список кнопок
     */
    @Composable
    fun ButtonsGroup(
        items: List<ButtonItem>,
        modifier: Modifier = Modifier,
        onSelected: (Int) -> Unit
    ) {
        Row(modifier, horizontalArrangement = Arrangement.Start) {
            items.forEach { item ->
                item.button(modifier, item.isChecked) {
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
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        ButtonLevelDown(
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        ButtonConfirmLevel(
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 3,
                    isChecked = checkId == 3,
                    button = { _, isChecked, onClick ->
                        ButtonLevelUp(
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(height = 63.dp),
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
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = TODAY_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        CheckedGradientButtonRightImage(
                            text = PICK_DATE_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(44.dp),
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
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = TODAY_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = TOMORROW_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 3,
                    isChecked = checkId == 3,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = PICK_DATE_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(44.dp),
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
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = PUBLIC_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        CheckedGradientButtonRightImage(
                            text = PRIVATE_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(44.dp),
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
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = MIX_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = MEN_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 3,
                    isChecked = checkId == 3,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = WOMEN_TEXT,
                            isChecked = isChecked,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(44.dp),
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
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = MALE_TEXT,
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = FEMALE_TEXT,
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(40.dp),
            onSelected = onSelected
        )
    }

    /**
     * группа кнопок выбора Level (из 4 кнопок: Light, Medium, Hard, Pro)
     */
    @Composable
    fun GroupButtonsForLevel(
        checkId: Int = 1,
        modifier: Modifier,
        onSelected: (Int) -> Unit
    ) {
        val paddingValues = PaddingValues(all = 10.dp)
        ButtonsGroup(
            listOf(
                ButtonItem(
                    position = 1,
                    isChecked = checkId == 1,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = LIGHT_TEXT,
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 2,
                    isChecked = checkId == 2,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = MEDIUM_TEXT,
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 3,
                    isChecked = checkId == 3,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = HARD_TEXT,
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                ),
                ButtonItem(
                    position = 4,
                    isChecked = checkId == 4,
                    button = { _, isChecked, onClick ->
                        CheckGradientButton(
                            text = PRO_TEXT,
                            isChecked = isChecked,
                            paddingValues = paddingValues,
                            onClick = onClick
                        )
                    }
                )
            ),
            modifier = modifier.height(height = 40.dp),
            onSelected = onSelected
        )
    }

    /**
     * группа кнопок выбора Tourney Type (из 2 кнопок: Individual, Team)
     */
    @Composable
    fun GroupButtonsForTourneyType(checkId: Int = 1, modifier: Modifier, onClick: () -> Unit) {
        Row(
            modifier = modifier.height(44.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CheckGradientButton(
                modifier = Modifier.weight(1f),
                text = INDIVIDUAL_TEXT,
                isChecked = checkId == 1,
                onClick = onClick
            )
            CheckGradientButton(
                modifier = Modifier.weight(1f),
                text = TEAM_TEXT,
                isChecked = checkId == 2,
                onClick = onClick
            )
        }
    }

    @Composable
    fun SliderButton(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean,
        onClick: () -> Unit
    ) {
        val paddingValues = PaddingValues(all = 4.dp)
        val color: Color = if (isChecked) {
            VolleyColor.White
        } else {
            Color.Transparent
        }
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(1.dp, Color.Transparent),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier,
            contentPadding = paddingValues,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = color, // Цвет фона
            ),
        ) {
            Text(
                text = text,
                color = VolleyColor.TextDark,
                style = ButtonSText
            )
        }
    }

    /**
     * слайдер-группа кнопок выбора Map|List
     */
    @Composable
    fun SliderButtonsMap(checkId: Int = 1, modifier: Modifier, onClick: () -> Unit) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
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
                .size(204.dp, 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SliderButton(
                    modifier = Modifier.size(100.dp, 28.dp),
                    text = MAP_TEXT,
                    isChecked = checkId == 1,
                    onClick = onClick
                )
                SliderButton(
                    modifier = Modifier.size(100.dp, 28.dp),
                    text = LIST_TEXT,
                    isChecked = checkId == 2,
                    onClick = onClick
                )
            }
        }
    }

    /**
     * слайдер-группа кнопок выбора All players|Favorites
     */
    @Composable
    fun SliderButtonsPlayers(checkId: Int = 1, modifier: Modifier, onClick: () -> Unit) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
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
                .size(319.dp, 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SliderButton(
                    modifier = Modifier.size(166.dp, 28.dp),
                    text = ALL_PLAYERS_TEXT,
                    isChecked = checkId == 1,
                    onClick = onClick
                )
                SliderButton(
                    modifier = Modifier.size(166.dp, 28.dp),
                    text = FAVORITES_TEXT,
                    isChecked = checkId == 2,
                    onClick = onClick
                )
            }
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
    PreviewContainer(
        modifier = Modifier
            .height(250.dp)
    ) {
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
            .width(350.dp)
    ) {
        Column {
            VolleyButton.CheckGradientButton(
                modifier = Modifier
                    .padding(24.dp)
                    .height(44.dp),
                isChecked = true,
                text = ISCHECKED_TRUE_TEXT,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
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
private fun PreviewCheckedGradientButtonRightImage() {
    PreviewContainer(
        modifier = Modifier
            .height(250.dp)
            .width(300.dp)
    ) {
        Column {
            VolleyButton.CheckedGradientButtonRightImage(
                modifier = Modifier
                    .height(44.dp),
                isChecked = true,
                text = ISCHECKED_TRUE_TEXT,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            VolleyButton.CheckedGradientButtonRightImage(
                modifier = Modifier
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
            checkId = 2,
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
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSliderButtonsMap() {
    PreviewContainer {
        SliderButtonsMap(
            modifier = Modifier,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSliderButtonsPlayers() {
    PreviewContainer(
        modifier = Modifier
            .width(350.dp)
    ) {
        SliderButtonsPlayers(
            modifier = Modifier,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewCombo() {
    VolleyContainersRootTransparent.Root {
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
                    onClick = {}
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
