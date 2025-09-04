package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField.MessageBubble
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButtonMap
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OutlinedActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.ui.theme.VolleybolleyTheme
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

// Обёртка для навигации
@Composable
fun MyGameScreen(navController: NavHostController) {
    val viewModel: MyGameViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                MyGameEffect.NavigateBack -> navController.popBackStack()
                is MyGameEffect.OpenMap -> openMap(context, effect.location)
                MyGameEffect.InvitePlayers,
                MyGameEffect.ShareLink,
                MyGameEffect.CancelGame -> {
                    // Заглушка
                }
            }
        }
    }

    MyGameContent(
        details = state.details,
        onBack = { viewModel.dispatch(MyGameAction.ClickBack) },
        onOpenMap = { location -> viewModel.dispatch(MyGameAction.ClickMap(location)) },
        onInvite = { viewModel.dispatch(MyGameAction.ClickInvite) },
        onShare = { viewModel.dispatch(MyGameAction.ClickShare) },
        onCancel = { viewModel.dispatch(MyGameAction.ClickCancel) },
        onDeletePlayer = { index -> viewModel.dispatch(MyGameAction.DeletePlayer(index)) }
    )
}

@Composable
private fun MyGameContent(
    details: GameDetails,
    onBack: () -> Unit,
    onOpenMap: (Location) -> Unit,
    onInvite: () -> Unit,
    onShare: () -> Unit,
    onCancel: () -> Unit,
    onDeletePlayer: (index: Int) -> Unit
) {
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    var message by remember { mutableStateOf(details.message) }
    val interactionSource = remember { MutableInteractionSource() }

    val (dateText, timeRangeText) = remember(details.startTime, details.endTime) {
        formatDateTimeRange(details.startTime, details.endTime)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .clickable(indication = null, interactionSource = interactionSource) {
                focusManager.clearFocus()
            }
    ) {
        Spacer(Modifier.height(VolleyDimens.DIMEN_116.dp))

        GlassCard(
            modifier = Modifier.padding(horizontal = VolleyDimens.DIMEN_8.dp),
            minHeight = VolleyDimens.DIMEN_380.dp
        ) {
            CardHeader(
                title = stringResource(R.string.my_game),
                onBack = onBack
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)
            ) {
                VolleyText.TitleMedium(stringResource(R.string.game_host), color = VolleyColor.White)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularAvatar(avatar = details.host.avatar, size = VolleyDimens.DIMEN_32.dp)
                    VolleyText.BodyBold(details.host.name, color = VolleyColor.White)
                    Spacer(Modifier.weight(1f))
                    LevelBadge(level = details.host.level)
                }

                VolleyMessageTextField.MessageField(
                    modifier = Modifier.fillMaxWidth(),
                    textInput = message,
                    maxLength = VolleyDimens.DIMEN_160,
                    hint = stringResource(R.string.type_your_message_hint)
                ) { newText -> message = newText }

                DividerGlass()

                SectionTitle(stringResource(R.string.about_game))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_nav_yellow_arrow),
                        contentDescription = null,
                        tint = VolleyColor.YellowPro,
                        modifier = Modifier.size(VolleyDimens.DIMEN_18.dp)
                    )
                    Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = VolleyDimens.DIMEN_12.dp)
                    ) {
                        VolleyText.BodyBold(details.courtLocation.courtName, color = VolleyColor.White)
                        VolleyText.BodyLight(details.courtLocation.locationName, color = VolleyColor.White)
                    }
                    MapChip { onOpenMap(details.courtLocation) }
                }

                LabeledInlineRow(stringResource(R.string.when_label), "$dateText, $timeRangeText")

                val levelText = details.levels.joinToString(", ")
                LabeledInlineRow(stringResource(R.string.level_label), levelText.ifBlank { "-" })

                LabeledInlineRow(stringResource(R.string.gender_label), details.gender)

                DividerGlass()

                SectionTitle(stringResource(R.string.payment))
                VolleyText.BodyRegular(
                    text = stringResource(
                        R.string.payment_type_and_account,
                        details.paymentType,
                        details.paymentAccount
                    ),
                    color = VolleyColor.White
                )
                MessageBubble(
                    text = stringResource(
                        R.string.per_person_amount,
                        details.pricePerPerson,
                        details.currencyType
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                DividerGlass()

                SectionTitle(stringResource(R.string.joined_players))
                PlayersList(
                    players = details.players,
                    capacity = details.maximumPlayers,
                    onDelete = onDeletePlayer
                )
            }
        }

        Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(VolleyDimens.DIMEN_192.dp)
                .padding(horizontal = VolleyDimens.DIMEN_8.dp),
            horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
        ) {
            SquareIconTile(
                iconRes = R.drawable.ic_invite_players,
                background = VolleyColor.YellowPro,
                onClick = onInvite,
                modifier = Modifier.weight(1f)
            )
            SquareIconTile(
                iconRes = R.drawable.ic_share_link,
                background = VolleyColor.White.copy(alpha = 0.10f),
                onClick = onShare,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

        OutlinedActiveButton(
            text = stringResource(R.string.cancel_game),
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = VolleyDimens.DIMEN_8.dp),
            paddingValues = PaddingValues(vertical = VolleyDimens.DIMEN_12.dp)
        )

        Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
    }
}

@SuppressLint("SimpleDateFormat")
private fun formatDateTimeRange(
    startIso: String,
    endIso: String
): Pair<String, String> {
    val locale = Locale.ENGLISH
    val parser = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).apply {
        timeZone = java.util.TimeZone.getDefault()
    }
    val startDate = parser.parse(startIso)
    val endDate = parser.parse(endIso)
    val dateFmt = java.text.SimpleDateFormat("d MMMM", locale)
    val timeFmt = java.text.SimpleDateFormat("h:mm a", locale)
    val date = dateFmt.format(startDate ?: "")
    val startTime = timeFmt.format(startDate ?: "").lowercase(locale)
    val endTime = timeFmt.format(endDate ?: "").lowercase(locale)
    return date to "$startTime–$endTime"
}

@Composable
private fun SquareIconTile(
    iconRes: Int,
    background: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(VolleyDimens.DIMEN_32.dp))
            .background(background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CardHeader(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(VolleyDimens.DIMEN_24.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(VolleyDimens.DIMEN_24.dp)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_icon_white),
                contentDescription = null,
                tint = VolleyColor.White,
                modifier = Modifier.size(
                    width = VolleyDimens.DIMEN_18.dp,
                    height = VolleyDimens.DIMEN_24.dp
                )
            )
        }
        VolleyText.TitleLarge(
            text = title,
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    minHeight: Dp = VolleyDimens.DIMEN_380.dp,
    cornerRadiusDp: Int = VolleyDimens.DIMEN_32,
    innerPadding: Dp = VolleyDimens.DIMEN_20.dp,
    gap: Dp = VolleyDimens.DIMEN_16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier,
        cornerRadius = cornerRadiusDp,
        mainContainerAlignment = Alignment.TopStart,
        contentContainerAlignment = Alignment.TopStart
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(gap),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}

@Composable
private fun MapChip(onClick: () -> Unit) {
    ActiveButtonMap(
        text = stringResource(R.string.map),
        onClick = onClick,
        modifier = Modifier
            .width(VolleyDimens.DIMEN_65.dp)
            .height(VolleyDimens.DIMEN_44.dp),
        paddingValues = PaddingValues(horizontal = VolleyDimens.DIMEN_16.dp)
    )
}

@Composable
fun LevelBadge(level: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(
                minWidth = VolleyDimens.DIMEN_30.dp,
                minHeight = VolleyDimens.DIMEN_23.dp
            )
            .background(
                color = VolleyColor.GreyDark,
                shape = RoundedCornerShape(VolleyDimens.DIMEN_10.dp)
            )
            .padding(
                start = VolleyDimens.DIMEN_10.dp,
                end = VolleyDimens.DIMEN_10.dp,
                top = VolleyDimens.DIMEN_2.dp,
                bottom = VolleyDimens.DIMEN_2.dp
            )
    ) {
        VolleyText.BodyRegular(
            text = level,
            color = VolleyColor.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    VolleyText.TitleMedium(text, color = VolleyColor.White)
}

@Composable
private fun LabeledInlineRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.BodyBold(text = label, color = VolleyColor.White)
        Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
        VolleyText.BodyRegular(text = value, color = VolleyColor.White)
    }
}

@Composable
private fun DividerGlass() {
    HorizontalDivider(
        thickness = VolleyDimens.DIMEN_1.dp,
        color = VolleyColor.White.copy(alpha = 0.25f)
    )
}

@Composable
private fun DeletePlayerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(VolleyDimens.DIMEN_21.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_remove),
            contentDescription = stringResource(R.string.remove_player_cd),
            modifier = Modifier.size(VolleyDimens.DIMEN_21.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun PlayersList(
    players: List<PlayerShort>,
    capacity: Int,
    onDelete: (index: Int) -> Unit
) {
    val items = buildList {
        addAll(players.map { it.name to it.level })
        repeat((capacity - players.size).coerceAtLeast(0)) { add(null to null) }
    }.take(capacity)

    Column(
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, pair ->
            val name = pair.first
            val level = pair.second
            val occupied = !name.isNullOrBlank()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = VolleyDimens.DIMEN_23.dp)
            ) {
                VolleyText.BodyRegular(
                    text = "${index + 1}. " + (name ?: stringResource(R.string.free_spot)),
                    color = VolleyColor.White,
                    modifier = Modifier.weight(1f)
                )

                if (occupied) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_2.dp)
                    ) {
                        DeletePlayerButton(onClick = { onDelete(index) })
                        level?.let { LevelBadge(it) }
                    }
                }
            }
        }
    }
}

//Карты
private fun openMap(context: Context, location: Location) {
    val uri = "geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(${
        Uri.encode(location.courtName)
    })".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val chooser = Intent.createChooser(intent, context.getString(R.string.open_with))
    context.startActivity(chooser)
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=1400dp,dpi=420"
)
@Composable
private fun MyGameScreen_Preview() {
    VolleybolleyTheme {
        Box(Modifier.background(VolleyColor.TurquoiseDark)) {
            MyGameScreen(
                navController = rememberNavController()
            )
        }
    }
}
