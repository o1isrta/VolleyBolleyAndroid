package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyCashField
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.Privacy
import cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen.GameEnteringConditionsScreenConstants
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TourneyEnteringConditionsScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToPayments: () -> Unit,
    onNavigateToChangeTeam: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: TourneyEnteringConditionsScreenViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is TourneyEnteringConditionsScreenEffect.ShowErrorMessage -> {
                Toast.makeText(context, "Error: ${currentEffect.message}", Toast.LENGTH_SHORT).show()
            }

            is TourneyEnteringConditionsScreenEffect.NavigateToPayments -> onNavigateToPayments()

            is TourneyEnteringConditionsScreenEffect.NavigateBack -> onNavigateBack()

            is TourneyEnteringConditionsScreenEffect.NavigateToSuccess -> onNavigateBack()

            is TourneyEnteringConditionsScreenEffect.NavigateToChangeTeam -> onNavigateToChangeTeam()

            null -> {}
        }
    }

    TourneyEnteringConditionsScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun TourneyEnteringConditionsScreen(
    state: TourneyEnteringConditionsScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (TourneyEnteringConditionsScreenEvent) -> Unit
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
                        title = stringResource(R.string.create_a_tourney),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        onBackClick = { eventCallback(TourneyEnteringConditionsScreenEvent.OnBackClicked) }
                    )

                    Column(
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        MaximumPlayersSection(
                            maximumPlayers = state.maximumPlayers,
                            onMaximumPlayersChanged = {
                                eventCallback(TourneyEnteringConditionsScreenEvent.MaximumPlayersChanged(it))
                            }
                        )

                        PrivacySection(
                            isPrivate = state.isPrivate,
                            onPrivacySelected = { privacy ->
                                eventCallback(
                                    when (privacy) {
                                        Privacy.Public -> TourneyEnteringConditionsScreenEvent.OnPublicSelected
                                        Privacy.Private -> TourneyEnteringConditionsScreenEvent.OnPrivateSelected
                                    }
                                )
                            }
                        )

                        PaymentSection(
                            perPerson = state.perPerson,
                            accountNumber = state.accountNumber,
                            onPerPersonChanged = {
                                eventCallback(TourneyEnteringConditionsScreenEvent.PerPersonChanged(it))
                            },
                            onAddPaymentClick = {
                                eventCallback(TourneyEnteringConditionsScreenEvent.OnAddPaymentClick)
                            }
                        )

                        VolleyButton.ActiveButton(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .height(44.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.save_game),
                            onClick = { eventCallback(TourneyEnteringConditionsScreenEvent.OnSaveTourneyClick) }
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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

    VolleyButton.GroupButtonsForPrivacy(
        modifier = Modifier.padding(top = 12.dp),
        checkId = if (isPrivate) GameEnteringConditionsScreenConstants.PRIVATE else GameEnteringConditionsScreenConstants.PUBLIC,
        onSelected = { position ->
            val selectedPrivacy = when (position) {
                GameEnteringConditionsScreenConstants.PUBLIC -> Privacy.Public
                GameEnteringConditionsScreenConstants.PRIVATE -> Privacy.Private
                else -> null
            }
            selectedPrivacy?.let { onPrivacySelected(it) }
        }
    )
}

@Composable
private fun PaymentSection(
    perPerson: String,
    accountNumber: String?,
    onPerPersonChanged: (String) -> Unit,
    onAddPaymentClick: () -> Unit
) {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier.padding(top = 20.dp)
    )

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
        } ?: VolleyButton.OutlinedActiveButtonSmallText(
            modifier = Modifier.height(35.dp),
            text = stringResource(R.string.add_payment),
            onClick = onAddPaymentClick
        )
    }
}

@Preview
@Composable
private fun TourneyEnteringConditionsScreenPreview() {
    val previewState = TourneyEnteringConditionsScreenState(
        maximumPlayers = 8,
        perPerson = "5.0",
        accountNumber = "123 45 6789"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        TourneyEnteringConditionsScreen(
            state = previewState,
            paddingFromSystemUi = PaddingValues(0.dp),
            eventCallback = {}
        )
    }
}
