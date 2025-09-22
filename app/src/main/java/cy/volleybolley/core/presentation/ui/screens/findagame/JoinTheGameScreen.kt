package cy.volleybolley.core.presentation.ui.screens.findagame

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButtonMap
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.navigation.SuccessRoute
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.referencedata.domain.model.CurrencyType
import cy.volleybolley.ui.theme.VolleybolleyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinTheGameScreen(
    navController: NavHostController,
    viewModel: JoinTheGameViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.obtainEvent(JoinTheGameEvent.OnRefresh) }
    ) {
        JoinTheGameScreen(
            state = state,
            effect = effect,
            navigationAction = { route ->
                route?.let {
                    navController.navigate(route)
                } ?: navController.popBackStack()
            },
            eventCallback = { event ->
                viewModel.obtainEvent(event)
            }
        )
    }
}

@Composable
private fun JoinTheGameScreen(
    state: JoinTheGameState,
    effect: JoinTheGameEffect?,
    navigationAction: (NavMap?) -> Unit,
    eventCallback: (JoinTheGameEvent) -> Unit,
) {
    val scroll = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            JoinTheGameEffect.JoinGame -> navigationAction(SuccessRoute)
            JoinTheGameEffect.NavigateBack -> navigationAction(null)
            is JoinTheGameEffect.OpenMap -> openMap(context = context, location = effect.location)
            null -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GlassCard(
            modifier = Modifier.padding(VolleyDimens.DIMEN_8.dp),
            minHeight = VolleyDimens.DIMEN_380.dp,
            scroll = scroll
        ) {
            CardHeader(
                title = stringResource(R.string.join_the_game)
            ) {
                eventCallback(JoinTheGameEvent.OnBack)
            }

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
                    CircularAvatar(avatar = state.details.host.avatar, size = VolleyDimens.DIMEN_32.dp)
                    VolleyText.BodyBold(state.details.host.name, color = VolleyColor.White)
                    Spacer(Modifier.weight(1f))
                    LevelBadge(level = state.details.host.level)
                }

                TransparentContainer(
                    cornerRadius = VolleyDimens.DIMEN_16
                ) {
                    VolleyText.BodyRegular(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(VolleyDimens.DIMEN_16.dp),
                        text = state.details.message,
                        color = VolleyColor.White
                    )
                }

                DividerGlass()

                SectionTitle(stringResource(R.string.about_game))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_geo),
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
                        VolleyText.BodyBold(state.details.courtLocation.courtName, color = VolleyColor.White)
                        VolleyText.BodyLight(state.details.courtLocation.locationName, color = VolleyColor.White)
                    }
                    MapChip { eventCallback(JoinTheGameEvent.OnMap(state.details.courtLocation)) }
                }

                LabeledInlineRow(stringResource(R.string.`when`), state.details.time)

                val levelText = state.details.levels.joinToString(", ")
                LabeledInlineRow(stringResource(R.string.level) + ":", levelText.ifBlank { "-" })

                LabeledInlineRow(stringResource(R.string.gender) + ":", state.details.gender)

                DividerGlass()

                SectionTitle(stringResource(R.string.payment))

                val paymentAccount = if (state.details.paymentAccount == null) {
                    PaymentType.CASH.nameValue.lowercase()
                        .replaceFirstChar { it.uppercaseChar() }
                } else {
                    "${
                        state.details.paymentType.nameValue.lowercase()
                            .replaceFirstChar { it.uppercaseChar() }
                    } · ${state.details.paymentAccount}"
                }

                VolleyText.BodyRegular(
                    text = paymentAccount,
                    color = VolleyColor.White
                )

                TransparentContainer(
                    cornerRadius = VolleyDimens.DIMEN_16
                ) {
                    VolleyText.BodyRegular(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(VolleyDimens.DIMEN_16.dp),
                        text = "${stringResource(R.string.per_person)} ${state.details.pricePerPerson}${state.details.currencyType.currencyValue}",
                        color = VolleyColor.White
                    )
                }

                DividerGlass()

                SectionTitle(stringResource(R.string.joined_players))
                PlayersList(
                    players = state.details.players,
                    capacity = state.details.maximumPlayers,
                )

                ActiveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = VolleyDimens.DIMEN_16.dp,
                        ),
                    text = stringResource(R.string.join_the_game),
                ) {

                }
            }
        }
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
    scroll: ScrollState,
    minHeight: Dp = VolleyDimens.DIMEN_380.dp,
    cornerRadiusDp: Int = VolleyDimens.DIMEN_32,
    innerPadding: Dp = VolleyDimens.DIMEN_20.dp,
    gap: Dp = VolleyDimens.DIMEN_16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    TransparentContainer(
        modifier = modifier,
        cornerRadius = cornerRadiusDp,
        mainContainerAlignment = Alignment.TopStart,
        contentContainerAlignment = Alignment.TopStart
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .verticalScroll(scroll)
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
private fun PlayersList(
    players: List<PlayerShort>,
    capacity: Int,
) {
    val items = buildList {
        addAll(players.map { it.name to it.level })
        repeat((capacity - players.size).coerceAtLeast(0)) { add(null to null) }
    }.take(capacity.coerceAtLeast(0))

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
    device = "spec:width=375dp,height=1200dp"
)
@Composable
private fun JoinTheGameScreenPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
                .padding(top = 116.dp)
        ) {
            JoinTheGameScreen(
                state = JoinTheGameState(
                    isRefreshing = false,
                    GameDetails(
                        gameId = 1,
                        gameType = "",
                        host = Host(
                            id = 1,
                            name = "Artem Ivanov",
                            avatar = null,
                            level = "L"
                        ),
                        message = "Hi! Just old friends meet at the court.",
                        courtLocation = Location(
                            longitude = 0.6,
                            latitude = 0.7,
                            courtName = "Karon Beach Club",
                            locationName = "Ratak Rd, Mueng Phuket"
                        ),
                        time = "10 October, 6:00-8:00",
                        gender = "Mix",
                        levels = listOf("Light"),
                        pricePerPerson = "5",
                        maximumPlayers = 5,
                        paymentType = PaymentType.THAIBANK,
                        paymentAccount = "988 016 7890",
                        currencyType = CurrencyType.EUR,
                        players = listOf(
                            PlayerShort(
                                playerId = 1,
                                name = "Artem Ivanov",
                                level = "L",
                                avatar = null
                            )
                        )
                    )
                ),
                effect = null,
                navigationAction = {},
                eventCallback = {}
            )
        }
    }
}

