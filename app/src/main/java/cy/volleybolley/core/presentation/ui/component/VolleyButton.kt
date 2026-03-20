package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ACTIVE_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupInvitesButtons
import cy.volleybolley.core.presentation.ui.component.VolleyButton.IS_CHECKED_FALSE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.IS_CHECKED_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.LEVEL_UP_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.MAP_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.PlayersFilter
import cy.volleybolley.core.presentation.ui.model.ViewTab
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonSText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonXSText
import cy.volleybolley.games.domain.model.entity.RatingType

@UiLibraryMarker
object VolleyButton {
    const val LEVEL_UP_TEXT = "Level up"
    const val LEVEL_DOWN_TEXT = "Level down"
    const val CONFIRM_LEVEL_TEXT = "Confirm level"
    const val ACTIVE_BUTTON_TEXT = "ACTIVE BUTTON"
    const val OUTLINED_BUTTON_TEXT = "OUTLINED BUTTON"
    const val MAP_TEXT = "Map"
    const val GRADIENT_BUTTON_TEXT = "Gradient button"
    const val OUTLINED_GRADIENT_BUTTON_TEXT = "Outlined gradient button"
    const val IS_CHECKED_TRUE_TEXT = "isChecked = true"
    const val IS_CHECKED_FALSE_TEXT = "isChecked = false"

    @Stable
    @Composable
    fun ActiveButton(
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        isLoading: Boolean = false,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
        onClick: () -> Unit
    ) {
        Button(
            enabled = enabled && !isLoading,
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.YellowPro,
                disabledContainerColor = VolleyColor.GreyDisabled
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = paddingValues
        ) {
            if (isLoading) {
                VolleyProgress.SmallCircularProgress()
            } else {
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
    }

    @Stable
    @Composable
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

    @Stable
    @Composable
    fun ActiveButtonMap(
        modifier: Modifier = Modifier,
        text: String,
        paddingValues: PaddingValues = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
        cornerRadius: Dp = 16.dp,
        onClick: () -> Unit
    ) {
        Button(
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.OrangeHard
            ),
            shape = RoundedCornerShape(cornerRadius),
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
    @Stable
    @Composable
    fun ActiveGradientButton(
        modifier: Modifier = Modifier,
        text: String = "",
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
        content: @Composable (() -> Unit)? = null,
        onClick: () -> Unit
    ) {
        Box(
            modifier = modifier
        ) {
            Button(
                onClick = onClick,
                modifier = Modifier.defaultMinSize(51.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
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
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                                    start = Offset(x = 0f, y = 0f),
                                    end = Offset(x = 0f, y = 100f)
                                ),
                            ),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    if (content == null) {
                        Text(
                            text = text,
                            color = VolleyColor.TextDark,
                            style = ButtonSText
                        )
                    } else {
                        content()
                    }
                }
            }
        }
    }

    /**
     * outlined градиентная кнопка без картинки
     */
    @Stable
    @Composable
    fun OutlinedGradientButton(
        modifier: Modifier = Modifier,
        text: String = "",
        paddingValues: PaddingValues =
            PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
        content: @Composable (() -> Unit)? = null,
        onClick: () -> Unit
    ) {
        Box(
            modifier = modifier
        ) {
            Button(
                onClick = onClick,
                modifier = Modifier.defaultMinSize(51.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = VolleyColor.White
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(colors = listOf(Color.Transparent, Color.Transparent)),
                        )
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient),
                                    start = Offset(x = 0f, y = 0f),
                                    end = Offset(x = 0f, y = 100f)
                                ),
                            ),
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    if (content == null) {
                        Text(
                            text = text,
                            color = VolleyColor.White,
                            style = ButtonSText
                        )
                    } else {
                        content()
                    }
                }
            }
        }
    }

    /**
     * используется для групп кнопок
     * @param isChecked кнопка выбрана/не выбрана
     */
    @Stable
    @Composable
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

    @Stable
    @Composable
    fun CheckedGradientButtonRightImage(
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
                content = {
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
                },
                text = text
            )
        } else {
            OutlinedGradientButton(
                onClick = onClick,
                modifier = modifier,
                paddingValues = paddingValues,
                content = {
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
                },
                text = text
            )
        }
    }

    /**
     * с картинкой наверху, вспомогательная функция для кнопок LevelUp, LevelDown, ConfirmLevel
     */
    @Stable
    @Composable
    fun CheckedGradientButtonTopImage(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean = false,
        iconPainter: Painter,
        paddingValues: PaddingValues = PaddingValues(12.dp, 8.dp, 12.dp, 8.dp),
        onClick: () -> Unit
    ) {
        if (isChecked) {
            ActiveGradientButton(
                onClick = onClick,
                modifier = modifier,
                paddingValues = paddingValues,
                content = {
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
                },
                text = text
            )
        } else {
            OutlinedGradientButton(
                onClick = onClick,
                modifier = modifier,
                paddingValues = paddingValues,
                content = {
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
                },
                text = text
            )
        }
    }

    @Stable
    @Composable
    fun ButtonLevelUp(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = LEVEL_UP_TEXT,
            isChecked = isChecked,
            iconPainter = if (isChecked) {
                painterResource(R.drawable.arrow_levelup_black)
            } else {
                painterResource(R.drawable.arrow_levelup_gradient)
            }
        )
    }

    @Stable
    @Composable
    fun ButtonLevelDown(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = LEVEL_DOWN_TEXT,
            isChecked = isChecked,
            iconPainter = if (isChecked) {
                painterResource(R.drawable.arrow_leveldown_black)
            } else {
                painterResource(R.drawable.arrow_leveldown_gradient)
            }
        )
    }

    @Stable
    @Composable
    fun ButtonConfirmLevel(
        modifier: Modifier = Modifier,
        isChecked: Boolean = false,
        onClick: () -> Unit
    ) {
        CheckedGradientButtonTopImage(
            onClick = onClick,
            modifier = modifier,
            text = CONFIRM_LEVEL_TEXT,
            isChecked = isChecked,
            iconPainter = if (isChecked) {
                painterResource(R.drawable.mark_black)
            } else {
                painterResource(R.drawable.mark_gradient)
            }
        )
    }

    @Stable
    @Composable
    fun GroupButtonsForChangeLevel(
        current: RatingType,
        modifier: Modifier,
        onSelected: (RatingType) -> Unit
    ) {
        Row(modifier.height(64.dp), horizontalArrangement = Arrangement.Start) {
            ButtonLevelDown(
                isChecked = current == RatingType.DOWN,
                onClick = { onSelected(RatingType.DOWN) }
            )
            ButtonConfirmLevel(
                isChecked = current == RatingType.CONFIRM,
                onClick = { onSelected(RatingType.CONFIRM) }
            )
            ButtonLevelUp(
                isChecked = current == RatingType.UP,
                onClick = { onSelected(RatingType.UP) }
            )
        }
    }

    /**
     * Generic функция для группы кнопок с одиночным выбором.
     *
     * @param items список элементов для отображения
     * @param selected текущий выбранный элемент
     * @param label функция для получения текста кнопки из элемента
     * @param modifier модификатор для контейнера
     * @param showRightIcon функция для определения, показывать ли правую иконку (стрелку) для элемента
     * @param paddingValues отступы внутри кнопок
     * @param onSelect callback при выборе элемента
     */
    @Stable
    @Composable
    fun <T> SingleChoiceButtonGroup(
        items: List<T>,
        selected: T,
        label: (T) -> String,
        modifier: Modifier = Modifier,
        showRightIcon: (T) -> Boolean = { false },
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
        onSelect: (T) -> Unit
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                if (showRightIcon(item)) {
                    CheckedGradientButtonRightImage(
                        text = label(item),
                        isChecked = selected == item,
                        paddingValues = paddingValues,
                        onClick = { onSelect(item) }
                    )
                } else {
                    CheckGradientButton(
                        text = label(item),
                        isChecked = selected == item,
                        paddingValues = paddingValues,
                        onClick = { onSelect(item) }
                    )
                }
            }
        }
    }

    /**
     * Generic функция для группы кнопок с множественным выбором.
     *
     * @param items список элементов для отображения
     * @param selected текущие выбранные элементы
     * @param label функция для получения текста кнопки из элемента
     * @param modifier модификатор для контейнера
     * @param paddingValues отступы внутри кнопок
     * @param onSelect callback при изменении выбора (возвращает новый Set)
     */
    @Stable
    @Composable
    fun <T> MultiChoiceButtonGroup(
        items: List<T>,
        selected: Set<T>,
        label: (T) -> String,
        modifier: Modifier = Modifier,
        paddingValues: PaddingValues = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
        onSelect: (Set<T>) -> Unit
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                CheckGradientButton(
                    text = label(item),
                    isChecked = selected.contains(item),
                    paddingValues = paddingValues,
                    onClick = {
                        val newSelected = selected.toMutableSet()
                        if (newSelected.contains(item)) {
                            newSelected.remove(item)
                        } else {
                            newSelected.add(item)
                        }
                        onSelect(newSelected)
                    }
                )
            }
        }
    }

    @Stable
    @Composable
    fun SliderButton(
        modifier: Modifier = Modifier,
        text: String,
        isChecked: Boolean,
        onClick: () -> Unit
    ) {
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(1.dp, Color.Transparent),
            shape = RoundedCornerShape(16.dp),
            modifier = modifier,
            contentPadding = PaddingValues(all = 4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isChecked) {
                    VolleyColor.White
                } else {
                    Color.Transparent
                }
            )
        ) {
            Text(
                text = text,
                color = VolleyColor.TextDark,
                style = ButtonSText
            )
        }
    }

    /**
     * Generic функция для слайдер-группы кнопок с одиночным выбором.
     *
     * @param items список элементов для отображения
     * @param selected текущий выбранный элемент
     * @param label функция для получения текста кнопки из элемента
     * @param modifier модификатор для контейнера
     * @param fillWidth если true - заполняет ширину, если false - фиксированный размер кнопок
     * @param buttonWidth ширина каждой кнопки (используется только при fillWidth = false)
     * @param height высота контейнера
     * @param onSelect callback при выборе элемента
     */
    @Stable
    @Composable
    fun <T> SliderButtonGroup(
        items: List<T>,
        selected: T,
        label: (T) -> String,
        modifier: Modifier = Modifier,
        fillWidth: Boolean = true,
        buttonWidth: Dp = 100.dp,
        height: Dp = 32.dp,
        onSelect: (T) -> Unit
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .then(
                    if (fillWidth) {
                        Modifier
                            .fillMaxWidth()
                            .height(height)
                    } else {
                        Modifier
                            .height(height)
                            .wrapContentSize()
                    }
                )
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
                modifier = Modifier
                    .then(
                        if (fillWidth) {
                            Modifier.fillMaxWidth()
                        } else {
                            Modifier
                        }
                    )
                    .padding(2.dp),
                horizontalArrangement = if (fillWidth) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.spacedBy(2.dp)
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEach { item ->
                    SliderButton(
                        modifier = if (fillWidth) {
                            Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        } else {
                            Modifier
                                .width(buttonWidth)
                                .fillMaxHeight()
                        },
                        text = label(item),
                        isChecked = selected == item,
                        onClick = { onSelect(item) }
                    )
                }
            }
        }
    }

    /**
     * Группа кнопок для приглашения игроков (и поделить ссылкой для приглашения)
     */
    @Stable
    @Composable
    fun GroupInvitesButtons(
        modifier: Modifier = Modifier,
        onInvitePlayersClick: () -> Unit,
        onShareLinkClick: () -> Unit
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InvitePlayersButton(
                modifier = Modifier.weight(1f),
                onClick = onInvitePlayersClick
            )
            ShareButton(
                modifier = Modifier.weight(1f),
                onClick = onShareLinkClick
            )
        }
    }

    @Stable
    @Composable
    private fun InvitePlayersButton(
        modifier: Modifier,
        onClick: () -> Unit
    ) {
        Button(
            modifier = modifier.height(180.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VolleyColor.YellowPro,
            ),
            contentPadding = PaddingValues(
                top = 20.dp,
                start = 20.dp
            ),
            shape = RoundedCornerShape(32.dp),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    painter = painterResource(R.drawable.image_invite_players),
                    contentDescription = null
                )
                VolleyText.TitleLarge(
                    modifier = Modifier.rotate(-8f),
                    text = stringResource(R.string.invite_players),
                    color = VolleyColor.TextDark
                )
            }
        }
    }

    @Stable
    @Composable
    private fun ShareButton(
        modifier: Modifier,
        onClick: () -> Unit
    ) {
        TransparentContainer(
            modifier = modifier.height(180.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                ),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 20.dp
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = onClick
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(R.drawable.image_share_link),
                        contentDescription = null
                    )
                    VolleyText.TitleLarge(
                        modifier = Modifier.rotate(-8f),
                        text = stringResource(R.string.share_link),
                        color = VolleyColor.White
                    )
                }
            }
        }
    }

    @Stable
    @Composable
    fun ActiveButtonWithLeadingIcon(
        backgroundColor: Color,
        icon: Painter,
        text: String,
        textColor: Color,
        modifier: Modifier = Modifier,
        isLoading: Boolean = false,
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            enabled = !isLoading,
            modifier = modifier.height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                disabledContainerColor = backgroundColor
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            } else {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            VolleyText.BodyBoldMedium(
                modifier = Modifier.padding(start = 12.dp),
                text = text,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Stable
    @Composable
    fun GradientTextButton(
        modifier: Modifier = Modifier,
        text: String,
        isEnable: Boolean = true,
        onClick: () -> Unit
    ) {
        TextButton(
            modifier = modifier.height(18.dp),
            enabled = isEnable,
            onClick = onClick,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    if (isEnable) {
                        withStyle(
                            SpanStyle(
                                brush = Brush.verticalGradient(
                                    listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
                                ),
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(text)
                        }
                    } else {
                        append(text)
                    }
                },
                color = VolleyColor.White,
                style = VolleyTypography.BodySmall
            )
        }
    }
}

@Stable
@Composable
fun PreviewContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .background(color = VolleyColor.TurquoiseDark)
            .width(350.dp)
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
                    .height(44.dp),
                isChecked = true,
                text = IS_CHECKED_TRUE_TEXT,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            VolleyButton.CheckGradientButton(
                modifier = Modifier
                    .height(44.dp),
                isChecked = false,
                text = IS_CHECKED_FALSE_TEXT,
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
                text = IS_CHECKED_TRUE_TEXT,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            VolleyButton.CheckedGradientButtonRightImage(
                modifier = Modifier
                    .height(44.dp),
                isChecked = false,
                text = IS_CHECKED_FALSE_TEXT,
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
            VolleyButton.ButtonLevelUp(onClick = {})
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.ButtonLevelUp(isChecked = true, onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonLevelDown() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.ButtonLevelDown(onClick = {})
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.ButtonLevelDown(isChecked = true, onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonConfirmLevel() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.ButtonConfirmLevel(onClick = {})
            Spacer(modifier = Modifier.size(12.dp))
            VolleyButton.ButtonConfirmLevel(isChecked = true, onClick = {})
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
            current = RatingType.UP,
            modifier = Modifier.padding(vertical = 12.dp),
            onSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSliderButtonsMap() {
    var selected by remember { mutableStateOf(ViewTab.Map) }
    PreviewContainer {
        VolleyButton.SliderButtonGroup(
            items = ViewTab.entries,
            selected = selected,
            label = { it.displayText },
            fillWidth = false,
            onSelect = { selected = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSliderButtonsPlayers() {
    var selected by remember { mutableStateOf(PlayersFilter.All) }
    PreviewContainer(
        modifier = Modifier
            .width(350.dp)
    ) {
        VolleyButton.SliderButtonGroup(
            items = PlayersFilter.entries,
            selected = selected,
            label = { it.displayText },
            onSelect = { selected = it }
        )
    }
}

@Preview
@Composable
fun PreviewInvitesButtons() {
    Box(
        modifier = Modifier
            .size(375.dp, 196.dp)
            .background(color = VolleyColor.TurquoiseDark)
    ) {
        GroupInvitesButtons(
            onInvitePlayersClick = {},
            onShareLinkClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewButtonWithLeadingIcon() {
    PreviewContainer(modifier = Modifier.width(300.dp)) {
        VolleyButton.ActiveButtonWithLeadingIcon(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = VolleyColor.White,
            icon = painterResource(id = R.drawable.ic_google_placeholder),
            text = stringResource(id = R.string.continue_with_google),
            textColor = VolleyColor.TextDark,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewGradientTextButton() {
    PreviewContainer(modifier = Modifier.height(250.dp)) {
        Column {
            VolleyButton.GradientTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.resend_in, "00:30"),
                isEnable = false,
                onClick = {}
            )
            VolleyButton.GradientTextButton(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.get_new_code),
                isEnable = true,
                onClick = {}
            )
        }
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
                    RatingType.UP,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    onSelected = {}
                )
                VolleyButton.MultiChoiceButtonGroup(
                    items = Level.entries,
                    selected = setOf(Level.Light, Level.Medium),
                    label = { level -> level.displayText },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.Start),
                    onSelect = {}
                )
                VolleyButton.OutlinedGradientButton(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "OutlinedGradientButton",
                    onClick = {}
                )
                VolleyButton.ActiveGradientButton(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "ActiveGradientButton",
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
