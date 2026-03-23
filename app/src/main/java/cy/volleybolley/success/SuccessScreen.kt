package cy.volleybolley.success

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun SuccessScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToInvitePlayers: (Int) -> Unit,
    viewModel: SuccessViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            SuccessEffect.CloseScreen -> onNavigateToHome()
            SuccessEffect.NavigateToInvitePlayers -> onNavigateToInvitePlayers(state.event.id)
            null -> Unit
        }
    }

    SuccessScreen(
        state = state,
        eventCallback = { event -> viewModel.obtainEvent(event) }
    )
}

@Stable
@Composable
private fun SuccessScreen(
    state: SuccessState,
    eventCallback: (SuccessEvent) -> Unit,
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TransparentContainer(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp
                ),
                cornerRadius = 32,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    val paymentAccount = if (state.event.paymentAccount == null) {
                        PaymentType.CASH.nameValue.lowercase()
                            .replaceFirstChar { it.uppercaseChar() }
                    } else {
                        "${
                            state.event.paymentType.nameValue.lowercase()
                                .replaceFirstChar { it.uppercaseChar() }
                        } · ${state.event.paymentAccount}"
                    }
                    Header(
                        title = when (state.event.type) {
                            SucceedGameType.CreatedGame -> R.string.game_created
                            SucceedGameType.CreatedTournament -> R.string.tourney_created
                            SucceedGameType.JoinedGame, SucceedGameType.JoinedTournament -> R.string.you_are_in_the_game
                        }
                    )
                    RowIconText(
                        painterResource = R.drawable.ic_geo,
                        title = state.event.locationName,
                        text = state.event.locationPlace,
                        iconSize = 16.dp
                    )
                    RowIconText(
                        painterResource = R.drawable.ic_clock,
                        title = state.event.date,
                        text = state.event.time
                    )
                    RowIconText(
                        painterResource = R.drawable.ic_ball,
                        title = state.event.level,
                        text = state.event.playersInfo
                    )
                    RowIconText(
                        painterResource = R.drawable.ic_payment_card,
                        title = state.event.pricePerPerson,
                        text = paymentAccount
                    )
                    ActiveButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp,
                                bottom = 8.dp
                            ),
                        text = stringResource(R.string.done),
                    ) {
                        eventCallback(SuccessEvent.OnDoneClick)
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VolleyButton.GroupInvitesButtons(
                    modifier = Modifier.padding(8.dp),
                    onInvitePlayersClick = {
                        eventCallback(SuccessEvent.OnInvitePlayers)
                    },
                    onShareLinkClick = {
                        shareEventLink(context, state.event)
                    }
                )
            }
        }
    }
}

@Composable
private fun Header(
    @StringRes title: Int
) {
    Column {
        VolleyText.TitleLarge(
            text = stringResource(title),
            color = VolleyColor.White
        )
    }
}

@Composable
private fun RowIconText(
    @DrawableRes painterResource: Int,
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
) {
    Row(
        modifier = modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(painterResource),
                modifier = Modifier.size(iconSize),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            VolleyText.BodyBold(text = title, color = VolleyColor.White)
            VolleyText.BodyLight(text = text, color = VolleyColor.White)
        }
    }
}

private fun shareEventLink(context: Context, event: SucceedGame) {
    val deepLink = event.toDeepLink()
    val shareText = when (event.type) {
        SucceedGameType.CreatedGame, SucceedGameType.JoinedGame ->
            context.getString(R.string.share_text_game, deepLink)

        SucceedGameType.CreatedTournament, SucceedGameType.JoinedTournament ->
            context.getString(R.string.share_text_tournament, deepLink)
    }
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    context.startActivity(
        Intent.createChooser(sendIntent, context.getString(R.string.share_link_chooser_title))
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=375dp,height=812dp"
)
@Composable
private fun SuccessScreenPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val state = SuccessState(
                SucceedGame(
                    id = 1,
                    type = SucceedGameType.JoinedGame,
                    locationName = "Karon Beach Club",
                    locationPlace = "Patak Rd, Mueng Phuket",
                    date = "Starts today",
                    time = "2:00-3:00 PM",
                    level = "Level: Light, Medium, Hard",
                    playersInfo = "Mix · 4 players · private game",
                    pricePerPerson = "5\$ per person",
                    paymentType = PaymentType.THAIBANK,
                    paymentAccount = "999999"
                )
            )
            SuccessScreen(
                state = state,
                eventCallback = {}
            )
        }
    }
}
