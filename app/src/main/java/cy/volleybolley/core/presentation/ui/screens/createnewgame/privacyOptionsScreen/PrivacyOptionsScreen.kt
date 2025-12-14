package cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.players.domain.model.Player
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PrivacyOptionsScreen(
    navController: NavHostController,
    viewModel: PrivacyOptionsScreenViewModel = viewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    ObserveUiEffects(viewModel, navController, context)

    if (state.isLoading) {
        VolleySimpleComponent.LoadingIndicator()
    } else {
        PrivacyOptionsContent(
            state = state,
            viewModel = viewModel,
            paddingFromSystemUi = paddingFromSystemUi
        )
    }
}

@Composable
private fun ObserveUiEffects(
    viewModel: PrivacyOptionsScreenViewModel,
    navController: NavHostController,
    context: Context
) {
    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is PrivacyOptionsScreenEffect.ShowError -> {
                    Log.d("GameEnteringConditionsScreen", "ShowError effect triggered: ${effect.message}")
                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_LONG).show()
                }

                PrivacyOptionsScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                // Обработка всех возможных случаев
                else -> {
                    // Handle unexpected effect or do nothing.  Log it!
                    Log.w("PrivacyOptionsScreen", "Unhandled effect: $effect")
                }
            }
        }
    }
}

@Composable
fun PrivacyOptionsContent(
    state: PrivacyOptionsScreenState,
    viewModel: PrivacyOptionsScreenViewModel,
    paddingFromSystemUi: PaddingValues
) {

    Column(
        modifier = Modifier.padding(paddingFromSystemUi)
    ) {
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
                    title = stringResource(R.string.private_game),
                    modifier = Modifier.fillMaxWidth(),
                    onBackClick = { viewModel.obtainEvent(PrivacyOptionsScreenEvent.OnBackClicked) }
                )

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                VolleyTextFieldGradient.SearchField(
                    modifier = Modifier,
                    text = state.query,
                    actionToTransferContent = { newQuery ->
                        viewModel.obtainEvent(PrivacyOptionsScreenEvent.OnQueryChanged(newQuery))
                    }
                ) { }

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                VolleyButton.SliderButtonsPlayers(
                    modifier = Modifier.fillMaxWidth(),
                    checkId = if (state.flagFavorites) 2 else 1,
                    onSelected = { position ->
                        val isFavorite = position == 2
                        viewModel.obtainEvent(PrivacyOptionsScreenEvent.AllOrFavoritesSelected(isFavorite))
                    }
                )

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                // передаем список найденных игроков + выбранных
                val filteredPlayers =
                    filterPlayers((state.selectedPlayers + state.playersSearchResult.toSet()).toList(), state.flagFavorites)
                val scrollState = rememberScrollState()

                Column(
                    verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_24.dp),
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    filteredPlayers.forEach { player ->
                        VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                            player = player,
                            isSelected = viewModel.isPlayerSelected(player),
                            onAction = {
                                viewModel.obtainEvent(PrivacyOptionsScreenEvent.OnPlayerSelectionClick(player))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_24.dp))
                VolleyButton.ActiveButton(
                    modifier = Modifier
                        .height(44.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    text = stringResource(R.string.add_selected),
                    onClick = { viewModel.obtainEvent(PrivacyOptionsScreenEvent.OnAddSelectedClick) }
                )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
            }
        }
    }
}

fun filterPlayers(players: List<Player>, flagFavorites: Boolean): List<Player> {
    // Отображаем всех игроков, если "только избранные" не выбрано
    return if (flagFavorites) {
        players.filter { it.isFavorite }
    } else {
        players
    }
}

@Preview
@Composable
private fun PrivacyOptionsScreenPreview() {
    val navController = rememberNavController() // Создаем моковый NavHostController
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        // GameEnteringConditionsScreen(navController = navController)
        PrivacyOptionsScreen(
            viewModel = PrivacyOptionsScreenViewModelPreview(),
            navController = navController, paddingFromSystemUi = PaddingValues(0.dp)
        )
    }
}
