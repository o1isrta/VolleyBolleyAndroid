package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PrivacyOptionsScreen(navController: NavHostController,
                         viewModel: PrivacyOptionsScreenViewModel = viewModel(),
                         paddingFromSystemUi: PaddingValues
                         ) {
    val scrollState = rememberScrollState() //Состояние скролла
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
//                is GameEnteringConditionsScreenEffect.ShowError -> {
//                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_SHORT).show()
//                }
//                GameEnteringConditionsScreenEffect.NavigateToPayments -> {
//                    navController.navigate(PaymentsRoute)
//                }
                PrivacyOptionsScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                //Обработка всех возможных случаев
                else -> {
                    // Handle unexpected effect or do nothing.  Log it!
                    Log.w("PrivacyOptionsScreen", "Unhandled effect: $effect")
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
        Column (
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
                        actionToTransferContent = {}
                    ) { }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                    VolleyButton.SliderButtonsPlayers(
                        modifier = Modifier.fillMaxWidth(),
                        checkId = when (state.flagFavorites) {
                            false -> 1
                            true -> 2
                        },
                        onSelected =
                        {
                            position ->
                            val isFavorite = when (position) {
                                1 -> false
                                2 -> true
                                else -> null // Обработка некорректной позиции
                            }
                            isFavorite?.let {
                                flag -> viewModel.obtainEvent(PrivacyOptionsScreenEvent.AllOrFavoritesSelected(flag))
                            } ?: run {
                                    // Обработка нераспознанной позиции
                                    Log.e("PrivacyOptionsScreen", "Нераспознанная позиция: $position")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    // список найденных игроков
                    Column(verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_24.dp)) {
                        state.playersSearchResult.forEachIndexed { index, player ->
                            VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                                player = player,
                                isSelected = state.playersSearchResult[index].is,
                                onAction = {
                                    viewModel.obtainEvent(PrivacyOptionsScreenEvent.OnPlayerClick(index))
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_24.dp))
                    VolleyButton.ActiveButton(
                        modifier = Modifier
                           // .padding(0.dp, VolleyDimens.DIMEN_8.dp, 0.dp, VolleyDimens.DIMEN_16.dp)
                            .height(44.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        text = stringResource(R.string.add_selected),
                        onClick = { viewModel.obtainEvent(PrivacyOptionsScreenEvent.OnAddSelectedClick) }
                    )
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
                }
                // Индикатор загрузки, если isLoading = true
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }
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
        PrivacyOptionsScreen(viewModel = PrivacyOptionsScreenViewModelPreview(),
            navController = navController, paddingFromSystemUi = PaddingValues(0.dp))
    }
}
