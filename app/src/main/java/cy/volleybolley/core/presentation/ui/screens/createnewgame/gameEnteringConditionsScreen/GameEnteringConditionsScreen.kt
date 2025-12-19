package cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyCashField
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.PrivacyOptionsRoute
import cy.volleybolley.core.presentation.ui.navigation.SuccessRoute
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.Privacy
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGame
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGameType
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json

@Composable
fun GameEnteringConditionsScreen(
    navController: NavHostController,
    viewModel: GameEnteringConditionsScreenViewModel = viewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val scrollState = rememberScrollState()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    //  Эффекты (навигация, показ ошибок)
    ObserveUiEffects(viewModel, navController, context)

    //  Отображение контента
    ContentDisplay(state, scrollState, paddingFromSystemUi, viewModel)
}

@Composable
private fun ObserveUiEffects(
    viewModel: GameEnteringConditionsScreenViewModel,
    navController: NavHostController,
    context: Context
) {
    LaunchedEffect(viewModel.uiEffect) {
        var str: String = R.string.game_entering_conditions_screen.toString()
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is GameEnteringConditionsScreenEffect.ShowError ->
                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_SHORT).show()

                is GameEnteringConditionsScreenEffect.NavigateToPayments ->
                    navController.navigate(PaymentsRoute)

                is GameEnteringConditionsScreenEffect.NavigateBack ->
                    navController.popBackStack()

                is GameEnteringConditionsScreenEffect.NavigateToSuccess ->
                    navigateToSuccess(navController)

                is GameEnteringConditionsScreenEffect.NavigateToPrivacy ->
                    navController.navigate(PrivacyOptionsRoute)

                else -> Log.w(
                    str,
                    "Unhandled effect: $effect"
                ) // Обработка неожиданных эффектов
            }
        }
    }
}

private fun navigateToSuccess(navController: NavHostController) {
    // Моковые данные (вынести в отдельную функцию/файл при необходимости)
    val succeed = SucceedGame(
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
    val succeedJson = Json.encodeToString(succeed)
    navController.navigate(SuccessRoute(succeedGame = succeedJson))
}

@Composable
private fun ContentDisplay(
    state: GameEnteringConditionsScreenState,
    scrollState: ScrollState,
    paddingFromSystemUi: PaddingValues,
    viewModel: GameEnteringConditionsScreenViewModel
) {
    if (state.isLoading) {
        VolleySimpleComponent.LoadingIndicator()
    } else {
        Column(
            modifier = Modifier.padding(paddingFromSystemUi)
        ) {
            VolleyContainersRootTransparent.TransparentContainer(
                cornerRadius = VolleyDimens.DIMEN_32,
                modifier = Modifier.padding(VolleyDimens.DIMEN_8.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = VolleyDimens.DIMEN_20.dp)) {
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                    TitleSection(viewModel)

                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        MaximumPlayersSection(state, viewModel)

                        PrivacySection(state, viewModel)

                        PlayersListSection(state, viewModel)
                    }

                    PaymentSection(state, viewModel)

                    SaveButtonSection(viewModel)

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
                }
            }
        }
    }
}

// Секции для ContentDisplay
@Composable
private fun TitleSection(viewModel: GameEnteringConditionsScreenViewModel) {
    TitleWithBackArrow(
        title = stringResource(R.string.create_a_game),
        modifier = Modifier.fillMaxWidth(),
        onBackClick = {
            viewModel.obtainEvent(
                GameEnteringConditionsScreenEvent.OnBackClicked
            )
        }
    )
    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
}

@Composable
private fun MaximumPlayersSection(
    state: GameEnteringConditionsScreenState,
    viewModel: GameEnteringConditionsScreenViewModel
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.maximum_players),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

    VolleyTextFieldAttribute.CountField(
        inputCount = state.maximumPlayers,
        actionToTransferCount = { newCount ->
            viewModel.obtainEvent(
                GameEnteringConditionsScreenEvent.MaximumPlayersChanged(newCount)
            )
        }
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
}

@Composable
private fun PrivacySection(
    state: GameEnteringConditionsScreenState,
    viewModel: GameEnteringConditionsScreenViewModel
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.privacy),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    VolleyText.BodyLight(
        text = stringResource(R.string.set_privacy_if_you_want_to_play_with_particular_players),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White,
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

    VolleyButton.GroupButtonsForPrivacy(
        checkId = when {
            state.players.isNotEmpty() -> 2 // Privacy.Private
            else -> 1 // Privacy.Public
        },
        modifier = Modifier.padding(vertical = 12.dp),
        onSelected = { position ->
            val selectedPrivacy = when (position) {
                1 -> Privacy.Public
                2 -> Privacy.Private
                else -> null // Обработка некорректной позиции
            }
            selectedPrivacy?.let { privacy ->
                when (privacy) {
                    Privacy.Public -> viewModel.obtainEvent(GameEnteringConditionsScreenEvent.OnPublicSelected)
                    Privacy.Private -> viewModel.obtainEvent(GameEnteringConditionsScreenEvent.OnPrivateSelected)
                }
            } ?: run {
                Log.e(R.string.game_entering_conditions_screen.toString(), "Unrecognized position: $position")
            }
        }
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
}

@Composable
private fun PlayersListSection(
    state: GameEnteringConditionsScreenState,
    viewModel: GameEnteringConditionsScreenViewModel
) {
    if (state.players.isNotEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_24.dp)) {
            state.players.forEachIndexed { index, player ->
                VolleySimpleComponent.PlayerRowWithRemove(
                    player = player,
                    onAction = {
                        viewModel.obtainEvent(
                            GameEnteringConditionsScreenEvent.RemovePlayer(index)
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_24.dp))
        VolleyButton.OutlinedGradientButton(
            modifier = Modifier.height(VolleyDimens.DIMEN_44.dp),
            text = stringResource(R.string.manage_players),
            onClick = {
                viewModel.obtainEvent(
                    GameEnteringConditionsScreenEvent.OnManagePlayersClick
                )
            }
        )
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
    }
}

@Composable
private fun PaymentSection(
    state: GameEnteringConditionsScreenState,
    viewModel: GameEnteringConditionsScreenViewModel
) {
    VolleySimpleComponent.DividerLine()

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

    VolleyText.TitleMedium(
        text = stringResource(R.string.payment),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    VolleyText.BodyLight(
        text = stringResource(R.string.enter_the_participation_fee_per_person),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White,
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
    ) {
        VolleyText.BodyBold(
            text = stringResource(R.string.per_person),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_9.dp))

        VolleyCashField.CashField(
            value = state.perPerson,
            currency = "$"
        ) { newValue ->
            viewModel.obtainEvent(
                GameEnteringConditionsScreenEvent.PerPersonChanged(
                    newValue
                )
            )
        }
    }

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.BodyRegular(
            text = stringResource(R.string.current_account),
            modifier = Modifier,
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_9.dp))

        state.accountNumber?.let {
            VolleyText.BodyRegular(
                text = it,
                modifier = Modifier,
                color = VolleyColor.White
            )
        } ?: run {
            VolleyButton.OutlinedActiveButtonSmallText(
                modifier = Modifier.height(VolleyDimens.DIMEN_35.dp),
                text = stringResource(R.string.add_payment),
                onClick = {
                    viewModel.obtainEvent(
                        GameEnteringConditionsScreenEvent.OnAddPaymentClick
                    )
                }
            )
        }
    }

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
}

@Composable
private fun SaveButtonSection(viewModel: GameEnteringConditionsScreenViewModel) {
    VolleyButton.ActiveButton(
        modifier = Modifier
            .height(VolleyDimens.DIMEN_44.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.save_game),
        onClick = {
            viewModel.obtainEvent(
                GameEnteringConditionsScreenEvent.OnSaveGameClick
            )
        }
    )
}

@Preview
@Composable
private fun GameEnteringConditionsScreenPreview() {
    val navController = rememberNavController() // Создаем моковый NavHostController
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        // GameEnteringConditionsScreen(navController = navController)
        GameEnteringConditionsScreen(
            viewModel = GameEnteringConditionsScreenViewModelPreview(),
            navController = navController,
            paddingFromSystemUi = PaddingValues(0.dp)
        )
    }
}
