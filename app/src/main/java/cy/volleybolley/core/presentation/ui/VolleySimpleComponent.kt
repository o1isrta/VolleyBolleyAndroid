package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.players.domain.model.Player

const val LEVEL_HIGH = "H"
const val LEVEL_LIGHT = "L"
const val LEVEL_MEDIUM = "M"
const val LEVEL_PRO = "P"

const val GENDER_MALE = "MALE"
const val GENDER_FEMALE = "FEMALE"

object VolleySimpleComponent {
    @Stable
    @Composable
    fun TitleWithBackArrow(
        modifier: Modifier = Modifier,
        title: String,
        onBackClick: () -> Unit = {}
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            VolleyText.TitleLarge(
                text = title,
                color = VolleyColor.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )

            Box(
                modifier = Modifier
                    .size(VolleyDimens.DIMEN_24.dp)
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = onBackClick
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = VolleyColor.White,
                )
            }
        }
    }
    /*
    fun TitleWithBackArrow(
        modifier: Modifier = Modifier,
        title: String,
        onBackClick: () -> Unit = {}
    ) {
        Row(
            modifier = modifier
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(VolleyDimens.DIMEN_24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.back_arrow_button),
                    tint = VolleyColor.White,
                )
            }

            VolleyText.TitleLarge(
                text = title,
                color = VolleyColor.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
    * */

    @Composable
    fun DividerLine(
        modifier: Modifier = Modifier,
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = VolleyColor.Divider,
            modifier = modifier
        )
    }

    // из 62 ветки, из файла ChangeTeamScreen.kt
    @Composable
    fun LevelBadge(level: String, modifier: Modifier = Modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_10.dp))
                .background(VolleyColor.GreyDark)
                //.padding(
                //    start = VolleyDimens.DIMEN_10.dp,
                //    end = VolleyDimens.DIMEN_10.dp,
                //    top = VolleyDimens.DIMEN_2.dp,
                //    bottom = VolleyDimens.DIMEN_2.dp
                //)
                .height(VolleyDimens.DIMEN_23.dp)
                .width(VolleyDimens.DIMEN_30.dp)
        ) {
            VolleyText.BodyRegular(level, color = VolleyColor.White)
        }
    }

    // элемент для выбора игроков. Отличается PlayerRow из СрфтпуеeamScreen порядком элементов
   /* @Composable
    @Stable
    fun PlayerCheckRow (
        member: MemberUi,
        showActions: Boolean,
        onRemove: () -> Unit
        Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
    .fillMaxWidth()
    .heightIn(min = VolleyDimens.DIMEN_23.dp)
    ) {
        VolleyText.BodyRegular(
            text = member.name ?: stringResource(R.string.free_spot),
            color = VolleyColor.White,
            modifier = Modifier.weight(1f)
        )

        if (showActions) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
            ) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_remove),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                    )
                }
                member.level?.let { LevelBadge(it) }
            }
        }
    }*/

    /*
    Строка игрока с кнопкой действия справа (удаление игрока или выбрать в команду)
    * */
    @Composable
    private fun PlayerRowWithAction(
        player: Player,
        icon: Painter, // иконка (удалить или выбрать игрока)
        onAction: () -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = VolleyDimens.DIMEN_23.dp)
        ) {
            VolleyText.BodyRegular(
                text = player.firstName + " " + player.lastName,// ?: stringResource(R.string.free_spot),
                color = VolleyColor.White,
                modifier = Modifier.weight(1f)
            )
            // if (showActions) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End//Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
            ) {
                player.level.let { LevelBadge(it) }
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                IconButton(
                    onClick = onAction,
                    modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                ) {
                    Icon(
                        painter = icon,//painterResource(R.drawable.ic_remove),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                    )
                }
            }
            // }
        }
    }

    /*
    Строка игрока с кнопкой "удалить" справа
     * */
    @Composable
    fun PlayerRowWithRemove(
        player: Player,
        onAction: () -> Unit
    ) {
        var icon = painterResource(R.drawable.ic_remove)
        PlayerRowWithAction(player, icon, onAction)
    }

    /*
    Строка игрока с кнопкой "выбрать" справа и значком isFavorite слева
     * */
    @Composable
    fun PlayerRowWithSelectAndFavorite(
        player: Player,
        isSelected: Boolean, // флаг, выбран игрок или нет
        onAction: () -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = VolleyDimens.DIMEN_23.dp)
        ) {
            FavoriteMark(player.isFavorite)
            Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
            val icon = if (isSelected) {
                painterResource(R.drawable.ic_payment_checkbox_fill) // Замените на вашу выбранную иконку
            } else {
                painterResource(R.drawable.ic_payment_checkbox_empty) // Замените на вашу невыбранную иконку
            }
            PlayerRowWithAction(player, icon, onAction)
        }
    }

    @Composable
    fun FavoriteMark(
        isFavorite: Boolean,
    ) {
        val painter = painterResource(
            if (isFavorite) R.drawable.ic_favorite_star_fill else R.drawable.ic_favorite_star_empty
        )
        Icon(
            modifier = Modifier.size(VolleyDimens.DIMEN_20.dp).padding(VolleyDimens.DIMEN_1.dp),
            contentDescription = null,
            painter = painter,
            tint = VolleyColor.OrangeHard
        )
    }
}

@Preview
@Composable
private fun PreviewTitleWithBackArrow() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleySimpleComponent.TitleWithBackArrow(
                    title = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.TitleWithBackArrow(
                    title = "Some long title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.DividerLine(
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLevelBadge() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleySimpleComponent.LevelBadge(
                    LEVEL_HIGH,
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.LevelBadge(
                    LEVEL_MEDIUM,
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.LevelBadge(
                    LEVEL_LIGHT,
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.LevelBadge(
                    LEVEL_PRO,
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPlayerRowWithRemoveList() {
    val players: List<Player> = listOf(
        Player(1,"Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
        Player(2, "Polina", "Vasylyeva", null,false, GENDER_FEMALE, LEVEL_PRO),
        Player(3, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
        Player(4, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH)
    )
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column(
                modifier = Modifier.padding(VolleyDimens.DIMEN_20.dp),
                verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)
            ) {
                players.forEachIndexed { index, player ->
                    VolleySimpleComponent.PlayerRowWithRemove(
                        player = player,
                        onAction = { }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPlayerRowWithSelectAndFavoriteList() {
    val players: List<Player> = listOf(
        Player(1,"Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
        Player(2, "Polina", "Vasylyeva", null,false, GENDER_FEMALE, LEVEL_PRO),
        Player(3, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
        Player(4, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH)
    )
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column(
                modifier = Modifier.padding(VolleyDimens.DIMEN_20.dp),
                verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)
            ) {
                VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                    player = players[0],
                    isSelected = true,
                    onAction = { }
                )
                VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                    player = players[1],
                    isSelected = false,
                    onAction = { }
                )
                VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                    player = players[2],
                    isSelected = true,
                    onAction = { }
                )
                VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                    player = players[3],
                    isSelected = true,
                    onAction = { }
                )
            }
        }
    }
}
