package cy.volleybolley.core.presentation.ui.screens.createNewGame.gameConditionsScreen

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyCashField
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.createNewGame.model.Privacy
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.success.SucceedGame
import cy.volleybolley.success.SucceedGameType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameConditionsScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToPayments: () -> Unit,
    onNavigateToPrivacyOptions: () -> Unit,
    onNavigateToSuccess: (SucceedGame) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: GameConditionsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is GameConditionsEffect.ShowErrorMessage -> {
                Toast.makeText(context, "Error: ${currentEffect.message}", Toast.LENGTH_SHORT).show()
            }
            is GameConditionsEffect.NavigateToPayments -> onNavigateToPayments()
            is GameConditionsEffect.NavigateBack -> onNavigateBack()
            is GameConditionsEffect.NavigateToSuccess -> onNavigateToSuccess(createSucceedGame())
            is GameConditionsEffect.NavigateToPrivacy -> onNavigateToPrivacyOptions()
            null -> {}
        }
    }

    GameConditionsScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

private fun createSucceedGame(): SucceedGame {
    return SucceedGame(
        id = 1213,
        type = SucceedGameType.CreatedGame,
        locationName = "Patak Rd, Mueang Phuket",
        locationPlace = "Karon Beach Club",
        date = "today",
        time = "2:00-3:00 pm",
        level = "Level: Light, Medium, Hard",
        playersInfo = "Mix · 4 players · private game",
        pricePerPerson = "5$",
        paymentType = PaymentType.CASH,
        paymentAccount = "123 45 6789"
    )
}

@Stable
@Composable
private fun GameConditionsScreen(
    state: GameConditionsState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (GameConditionsEvent) -> Unit
) {
    if (state.isLoading) {
        VolleySimpleComponent.LoadingIndicator()
    } else {
        Column(
            modifier = Modifier.padding(paddingFromSystemUi)
        ) {
            VolleyContainersRootTransparent.TransparentContainer(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    TitleWithBackArrow(
                        title = stringResource(R.string.create_a_game),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        onBackClick = { eventCallback(GameConditionsEvent.OnBackClicked) }
                    )

                    val scrollState = rememberSaveable(saver = ScrollState.Saver) {
                        ScrollState(0)
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .verticalScroll(scrollState)
                    ) {
                        MaximumPlayersSection(
                            maximumPlayers = state.maximumPlayers,
                            onMaximumPlayersChanged = {
                                eventCallback(GameConditionsEvent.MaximumPlayersChanged(it))
                            }
                        )

                        PrivacySection(
                            isPrivate = state.isPrivate,
                            onPrivacySelected = { privacy ->
                                eventCallback(
                                    when (privacy) {
                                        Privacy.Public -> GameConditionsEvent.OnPublicSelected
                                        Privacy.Private -> GameConditionsEvent.OnPrivateSelected
                                    }
                                )
                            }
                        )

                        PlayersListSection(
                            players = state.players,
                            onRemovePlayer = { index ->
                                eventCallback(GameConditionsEvent.RemovePlayer(index))
                            },
                            onManagePlayersClick = {
                                eventCallback(GameConditionsEvent.OnManagePlayersClick)
                            }
                        )

                        PaymentSection(
                            perPerson = state.perPerson,
                            accountNumber = state.accountNumber,
                            onPerPersonChanged = {
                                eventCallback(GameConditionsEvent.PerPersonChanged(it))
                            },
                            onAddPaymentClick = {
                                eventCallback(GameConditionsEvent.OnAddPaymentClick)
                            }
                        )

                        VolleyButton.ActiveButton(
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .height(44.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.save_game),
                            onClick = { eventCallback(GameConditionsEvent.OnSaveGameClick) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MaximumPlayersSection(
    maximumPlayers: Int,
    onMaximumPlayersChanged: (Int) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.maximum_players),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Box(modifier = Modifier.padding(top = 12.dp)) {
        VolleyTextFieldAttribute.CountField(
            inputCount = maximumPlayers,
            actionToTransferCount = onMaximumPlayersChanged
        )
    }
}

@Composable
private fun PrivacySection(
    isPrivate: Boolean,
    onPrivacySelected: (Privacy) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.privacy),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        color = VolleyColor.White
    )

    VolleyText.BodyLight(
        text = stringResource(R.string.set_privacy_if_you_want_to_play_with_particular_players),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White,
        textAlign = TextAlign.Left
    )

    VolleyButton.SingleChoiceButtonGroup(
        items = Privacy.entries,
        selected = if (isPrivate) Privacy.Private else Privacy.Public,
        label = { it.displayText },
        showRightIcon = { it.showRightIcon },
        modifier = Modifier.padding(top = 12.dp),
        onSelect = onPrivacySelected
    )
}

@Composable
private fun PlayersListSection(
    players: List<Player>,
    onRemovePlayer: (Int) -> Unit,
    onManagePlayersClick: () -> Unit
) {
    if (players.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            players.forEachIndexed { index, player ->
                VolleySimpleComponent.PlayerRowWithRemove(
                    player = player,
                    onAction = { onRemovePlayer(index) }
                )
            }
        }

        VolleyButton.OutlinedGradientButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .height(44.dp),
            text = stringResource(R.string.manage_players),
            onClick = onManagePlayersClick
        )
    }
}

@Composable
private fun PaymentSection(
    perPerson: String,
    accountNumber: String?,
    onPerPersonChanged: (String) -> Unit,
    onAddPaymentClick: () -> Unit
) {
    VolleySimpleComponent.DividerLine(Modifier.padding(top = 20.dp))

    VolleyText.TitleMedium(
        text = stringResource(R.string.payment),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        color = VolleyColor.White
    )

    VolleyText.BodyLight(
        text = stringResource(R.string.enter_the_participation_fee_per_person),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White,
        textAlign = TextAlign.Left
    )

    Row(
        modifier = Modifier.padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VolleyText.BodyBold(
            text = stringResource(R.string.per_person),
            color = VolleyColor.White
        )

        Box(modifier = Modifier.padding(start = 9.dp)) {
            VolleyCashField.CashField(
                value = perPerson,
                currency = "$",
                onValueChanged = onPerPersonChanged
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        VolleyText.BodyRegular(
            text = stringResource(R.string.current_account),
            color = VolleyColor.White
        )

        accountNumber?.let {
            VolleyText.BodyRegular(
                text = it,
                color = VolleyColor.White
            )
        } ?: VolleyButton.OutlinedGradientButton(
            modifier = Modifier.height(35.dp),
            paddingValues = PaddingValues(16.dp, 8.dp, 16.dp, 8.dp),
            text = stringResource(R.string.add_payment),
            onClick = onAddPaymentClick
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun GameEnteringConditionsScreenPreview() {
    val previewState = GameConditionsState(
        maximumPlayers = 8,
        perPerson = "5.0",
        accountNumber = "123 45 6789"
    )

    RootContainerForPreview {
        GameConditionsScreen(
            state = previewState,
            paddingFromSystemUi = it,
            eventCallback = {}
        )
    }
}
