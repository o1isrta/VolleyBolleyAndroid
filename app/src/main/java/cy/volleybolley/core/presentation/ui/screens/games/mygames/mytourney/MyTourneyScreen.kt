package cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField.MessageBubble
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButtonMap
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveGradientButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupInvitesButtons
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OutlinedActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OutlinedGradientButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

// Обёртка
@Composable
fun MyTourneyScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToManagePlayers: () -> Unit,
    onNavigateToChangeTeam: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: MyTourneyViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            null -> {}
            is MyTourneyEffect.NavigateBack -> onNavigateBack()
            is MyTourneyEffect.NavigateToManagePlayers -> onNavigateToManagePlayers()
            is MyTourneyEffect.NavigateToChangeTeam -> onNavigateToChangeTeam()
            is MyTourneyEffect.OpenMap -> openMap(context, currentEffect.location)
            is MyTourneyEffect.InvitePlayers -> { /* TODO */ }
            is MyTourneyEffect.ShareLink -> { /* TODO */ }
            is MyTourneyEffect.CancelEvent -> { /* TODO */ }
        }
    }

    MyTourneyContent(
        details = state.details,
        paddingFromSystemUi = paddingFromSystemUi,
        onBack = { viewModel.obtainEvent(MyTourneyAction.ClickBack) },
        onOpenMap = { viewModel.obtainEvent(MyTourneyAction.ClickMap(it)) },
        onInvite = { viewModel.obtainEvent(MyTourneyAction.ClickInvite) },
        onShare = { viewModel.obtainEvent(MyTourneyAction.ClickShare) },
        onCancel = { viewModel.obtainEvent(MyTourneyAction.ClickCancel) },
        onPlayersOrTeams = { viewModel.obtainEvent(MyTourneyAction.ClickPlayersOrTeams) }
    )
}

@Stable
@Composable
private fun MyTourneyContent(
    details: TournamentDetails,
    paddingFromSystemUi: PaddingValues,
    onBack: () -> Unit,
    onOpenMap: (Location) -> Unit,
    onInvite: () -> Unit,
    onShare: () -> Unit,
    onCancel: () -> Unit,
    onPlayersOrTeams: () -> Unit
) {
    val scroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    var message by rememberSaveable { mutableStateOf(details.message) }
    val interactionSource = remember { MutableInteractionSource() }
    val (dateFormatted, timeFormatted) = remember(details.startTime, details.endTime) {
        formatDateTimeRange(details.startTime, details.endTime)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
            .verticalScroll(scroll)
            .clickable(indication = null, interactionSource = interactionSource) {
                focusManager.clearFocus()
            }
    ) {
        GlassCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            minHeight = 380.dp
        ) {
            CardHeader(
                title = stringResource(R.string.my_tourney),
                onBack = onBack
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                VolleyText.TitleMedium("Tourney host", color = VolleyColor.White)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularAvatar(avatar = details.host.avatar, size = 32.dp)
                    VolleyText.BodyBold(details.host.name, color = VolleyColor.White)
                    Box(Modifier.weight(1f))
                    LevelBadge(level = details.host.level)
                }

                VolleyMessageTextField.MessageField(
                    modifier = Modifier.fillMaxWidth(),
                    textInput = message,
                    maxLength = 160,
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
                        modifier = Modifier.size(18.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 12.dp)
                    ) {
                        VolleyText.BodyBold(details.courtLocation.courtName, color = VolleyColor.White)
                        VolleyText.BodyLight(details.courtLocation.locationName, color = VolleyColor.White)
                    }
                    MapChip { onOpenMap(details.courtLocation) }
                }

                LabeledInlineRow(stringResource(R.string.when_label), "$dateFormatted, $timeFormatted")

                val levelText = details.levels.joinToString(", ").ifBlank { "-" }
                LabeledInlineRow(stringResource(R.string.level_label), levelText)

                LabeledInlineRow(stringResource(R.string.gender_label), details.gender)

                DividerGlass()

                SectionTitle(stringResource(R.string.payment))
                if (!details.paymentAccount.isNullOrBlank()) {
                    VolleyText.BodyRegular(
                        text = "${details.paymentType} · ${details.paymentAccount}",
                        color = VolleyColor.White
                    )
                } else {
                    VolleyText.BodyRegular(text = details.paymentType, color = VolleyColor.White)
                }
                MessageBubble(
                    text = stringResource(
                        R.string.per_person_amount,
                        details.pricePerPerson,
                        details.currencyType
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                DividerGlass()

                SectionTitle(text = stringResource(R.string.joined_players_))

                val btnMod = Modifier
                    .width(116.dp)
                    .height(44.dp)
                    .clip(RoundedCornerShape(16.dp))

                if (details.isIndividual) {
                    Box(modifier = btnMod.clickable { onPlayersOrTeams() }) {
                        OutlinedGradientButton(
                            text = "",
                            modifier = Modifier.matchParentSize(),
                            paddingValues = PaddingValues(0.dp),
                            onClick = onPlayersOrTeams
                        )
                        Row(
                            modifier = Modifier
                                .matchParentSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VolleyText.BodyRegular("Players", color = VolleyColor.White)
                            Box(Modifier.weight(1f))
                            Icon(
                                painter = painterResource(R.drawable.arrow_right_white),
                                contentDescription = null,
                                tint = VolleyColor.White,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                } else {
                    Box(modifier = btnMod.clickable { onPlayersOrTeams() }) {
                        ActiveGradientButton(
                            text = "",
                            modifier = Modifier.matchParentSize(),
                            paddingValues = PaddingValues(0.dp),
                            onClick = onPlayersOrTeams
                        )
                        Row(
                            modifier = Modifier
                                .matchParentSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VolleyText.BodyRegular("Teams", color = VolleyColor.TextDark)
                            Box(Modifier.weight(1f))
                            Icon(
                                painter = painterResource(R.drawable.arrow_right_black),
                                contentDescription = null,
                                tint = VolleyColor.TextDark,
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        GroupInvitesButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp),
            onInvitePlayersClick = onInvite,
            onShareLinkClick = onShare
        )

        OutlinedActiveButton(
            text = stringResource(R.string.cancel_game),
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp),
            paddingValues = PaddingValues(vertical = 12.dp)
        )

        Box(Modifier.padding(bottom = 16.dp))
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
            .height(24.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_icon_white),
                contentDescription = null,
                tint = VolleyColor.White,
                modifier = Modifier.size(width = 18.dp, height = 24.dp)
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
    minHeight: Dp = 380.dp,
    cornerRadiusDp: Int = 32,
    innerPadding: Dp = 20.dp,
    gap: Dp = 16.dp,
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
            .width(65.dp)
            .height(44.dp),
        paddingValues = PaddingValues(horizontal = 16.dp)
    )
}

@Composable
fun LevelBadge(level: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(minWidth = 30.dp, minHeight = 23.dp)
            .background(color = VolleyColor.GreyDark, shape = RoundedCornerShape(10.dp))
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 2.dp,
                bottom = 2.dp
            )
    ) {
        VolleyText.BodyRegular(text = level, color = VolleyColor.White, textAlign = TextAlign.Center)
    }
}

@Composable
private fun SectionTitle(text: String) {
    VolleyText.TitleMedium(text, color = VolleyColor.White)
}

@Composable
private fun LabeledInlineRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        VolleyText.BodyBold(text = label, color = VolleyColor.White)
        VolleyText.BodyRegular(
            text = value,
            color = VolleyColor.White,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun DividerGlass() {
    HorizontalDivider(thickness = 1.dp, color = VolleyColor.White)
}

private fun formatDateTimeRange(startIso: String, endIso: String): Pair<String, String> {
    val locale = Locale.ENGLISH
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).apply {
        timeZone = java.util.TimeZone.getDefault()
    }
    val start = parser.parse(startIso)
    val end = parser.parse(endIso)
    val dateFmt = SimpleDateFormat("d MMMM", locale)
    val timeFmt = SimpleDateFormat("h:mm a", locale)
    val date = dateFmt.format(start ?: 0)
    val startTime = timeFmt.format(start ?: 0).lowercase(locale)
    val endTime = timeFmt.format(end ?: 0).lowercase(locale)
    return date to "$startTime–$endTime"
}

// Карты
private fun openMap(context: Context, location: Location) {
    val uri = "geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(${
        Uri.encode(location.courtName)
    })".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val chooser = Intent.createChooser(intent, context.getString(R.string.open_with))
    context.startActivity(chooser)
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun MyTourneyScreen_Preview() {
    RootContainerForPreview {
        MyTourneyContent(
            details = myTourneyStub(),
            paddingFromSystemUi = PaddingValues(0.dp),
            onBack = {},
            onOpenMap = {},
            onInvite = {},
            onShare = {},
            onCancel = {},
            onPlayersOrTeams = {}
        )
    }
}
