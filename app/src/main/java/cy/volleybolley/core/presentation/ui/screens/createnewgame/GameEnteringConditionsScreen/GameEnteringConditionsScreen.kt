package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyCashField
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.LevelBadge
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OUTLINED_GRADIENT_BUTTON_TEXT
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.SuccessRoute
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGame
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGameType
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json

@Composable
fun GameEnteringConditionsScreen(navController: NavHostController,
                                 viewModel: GameEnteringConditionsScreenViewModel = GameEnteringConditionsScreenViewModel()) {

    val scrollState = rememberScrollState() //Состояние скролла
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is GameEnteringConditionsScreenEffect.ShowError -> {
                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_SHORT).show()
                }
                GameEnteringConditionsScreenEffect.NavigateToPayments -> {
                    navController.navigate(PaymentsRoute)
                }
                GameEnteringConditionsScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                GameEnteringConditionsScreenEffect.NavigateToSuccess -> {
                    // моковые данные
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
                //Обработка всех возможных случаев
                else -> {
                    // Handle unexpected effect or do nothing.  Log it!
                    Log.w("GameEnteringConditionsScreen", "Unhandled effect: $effect")
                }
            }
        }
    }

    // Overlay для отображения индикатора загрузки
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Или другой индикатор загрузки
        }
    } else {  // Отображаем основной контент, только если не загружается
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_32,
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = VolleyDimens.DIMEN_20.dp)
            ) {
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                TitleWithBackArrow(
                    title = stringResource(R.string.create_a_game),
                    modifier = Modifier.fillMaxWidth(),
                    onBackClick = { viewModel.obtainEvent(GameEnteringConditionsScreenEvent.OnBackClicked)}
                )

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
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
                            viewModel.obtainEvent(GameEnteringConditionsScreenEvent.MaximumPlayersChanged(newCount))
                        }
                    )

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

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
                        checkId = when (state.selectedPrivacy) {
                            Privacy.Public -> 1
                            Privacy.Private -> 2
                                                               },
                        modifier = Modifier.padding(vertical = 12.dp),
                        onSelected = { position ->
                            val selectedPrivacy = when (position) {
                                1 -> Privacy.Public
                                2 -> Privacy.Private
                                else -> null // Обработка некорректной позиции
                            }
                            selectedPrivacy?.let {
                                viewModel.obtainEvent(GameEnteringConditionsScreenEvent.PrivacySelected(it))
                            } ?: run {
                                // Обработка нераспознанной позиции
                                Log.e("GameEnteringConditionsScreen", "Нераспознанная позиция: $position")
                              }
                        }
                    )
                    // список выбранных игроков и кнопка Manage players
                    if (state.selectedPrivacy == Privacy.Private){
                        Column(verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)) {
                            state.players.forEachIndexed { index, player ->
                                PlayerRowWithRemove(
                                    player = player,
                                    //showActions = member.name != null,
                                    onRemove = {
                                        viewModel.obtainEvent(GameEnteringConditionsScreenEvent.RemovePlayer(index))
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
                        VolleyButton.OutlinedGradientButton(
                            modifier = Modifier.height(44.dp),
                            text = stringResource(R.string.manage_players),
                            onClick = {}
                        )
                    }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

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
                        modifier = Modifier//.fillMaxWidth()
                    ) {
                        VolleyText.BodyBold(
                            text = stringResource(R.string.per_person),
                            color = VolleyColor.White
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_9.dp))

                        VolleyCashField.CashField(
                            value = state.perPerson,
                            currency = "$"
                        ) { newValue -> viewModel.obtainEvent(GameEnteringConditionsScreenEvent.PerPersonChanged(newValue)) }
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
                                modifier = Modifier.height(35.dp),
                                text = stringResource(R.string.add_payment),
                                onClick = { viewModel.obtainEvent(GameEnteringConditionsScreenEvent.OnAddPaymentClick) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                    VolleyButton.ActiveButton(
                        modifier = Modifier
                            .padding(0.dp, VolleyDimens.DIMEN_8.dp, 0.dp, VolleyDimens.DIMEN_16.dp)
                            .height(44.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        text = stringResource(R.string.save_game),
                        onClick = {viewModel.obtainEvent(GameEnteringConditionsScreenEvent.OnSaveGameClick)}
                    )
                }
            }
        }
    }
}

@Composable
private fun PlayerRowWithRemove(
    player: Player,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = VolleyDimens.DIMEN_23.dp)
    ) {
        VolleyText.BodyRegular(
            text = player.name,// ?: stringResource(R.string.free_spot),
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
            }
       // }
    }
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
        GameEnteringConditionsScreen(navController = navController)
    }
}
