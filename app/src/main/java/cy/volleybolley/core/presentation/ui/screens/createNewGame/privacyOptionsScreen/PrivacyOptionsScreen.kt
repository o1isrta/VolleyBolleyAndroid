package cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.PlayersFilter
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.Gender
import cy.volleybolley.players.domain.model.Player
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PrivacyOptionsScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateBack: () -> Unit,
    viewModel: PrivacyOptionsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    val resources = LocalResources.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is PrivacyOptionsEffect.ShowErrorMessage -> {
                Toast.makeText(context, "Error: ${currentEffect.message}", Toast.LENGTH_LONG).show()
            }

            is PrivacyOptionsEffect.ShowErrorMessageById -> {
                val errorMessage = resources.getString(currentEffect.messageId)
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }

            is PrivacyOptionsEffect.NavigateBack -> {
                onNavigateBack()
            }

            null -> {}
        }
    }

    PrivacyOptionsScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        isPlayerSelected = { player -> viewModel.isPlayerSelected(player) },
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun PrivacyOptionsScreen(
    state: PrivacyOptionsState,
    paddingFromSystemUi: PaddingValues,
    isPlayerSelected: (Player) -> Boolean,
    eventCallback: (PrivacyOptionsEvent) -> Unit
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
                        title = stringResource(R.string.private_game),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        onBackClick = { eventCallback(PrivacyOptionsEvent.OnBackClicked) }
                    )

                    val scrollState = rememberSaveable(saver = ScrollState.Saver) {
                        ScrollState(0)
                    }
                    Column(
                        modifier = Modifier.verticalScroll(scrollState)
                    ) {
                        VolleyTextFieldGradient.SearchField(
                            modifier = Modifier.padding(top = 16.dp),
                            text = state.query,
                            actionToTransferContent = { eventCallback(PrivacyOptionsEvent.OnQueryChanged(it)) }
                        ) { }

                        VolleyButton.SliderButtonGroup(
                            items = PlayersFilter.entries,
                            selected = if (state.flagFavorites) PlayersFilter.Favorites else PlayersFilter.All,
                            label = { it.displayText },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            onSelect = { filter ->
                                eventCallback(PrivacyOptionsEvent.AllOrFavoritesSelected(
                                    isFavorites = filter == PlayersFilter.Favorites
                                ))
                            }
                        )

                        Column(
                            modifier = Modifier.padding(top = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            state.filteredPlayers.forEach { player ->
                                VolleySimpleComponent.PlayerRowWithSelectAndFavorite(
                                    player = player,
                                    isSelected = isPlayerSelected(player),
                                    onAction = { eventCallback(PrivacyOptionsEvent.OnPlayerSelectionClick(player)) }
                                )
                            }
                        }

                        VolleyButton.ActiveButton(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .height(44.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            text = stringResource(R.string.add_selected),
                            onClick = { eventCallback(PrivacyOptionsEvent.OnAddSelectedClick) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrivacyOptionsScreenPreview() {
    val previewState = PrivacyOptionsState(
        playersSearchResult = listOf(
            Player(
                id = 1,
                firstName = "John",
                lastName = "Doe",
                avatarUrl = null,
                isFavorite = true,
                level = Level.Medium.displayText.first().toString(),
                gender = Gender.Men.displayText.first().toString()
            ),
            Player(
                id = 2,
                firstName = "Jane",
                lastName = "Smith",
                avatarUrl = null,
                isFavorite = false,
                level = "L",
                gender = "F"
            )
        ),
        filteredPlayers = listOf(
            Player(
                id = 1,
                firstName = "John",
                lastName = "Doe",
                avatarUrl = null,
                isFavorite = true,
                level = Level.Medium.displayText.first().toString(),
                gender = Gender.Men.displayText.first().toString()
            ),
            Player(
                id = 2,
                firstName = "Jane",
                lastName = "Smith",
                avatarUrl = null,
                isFavorite = false,
                level = "L",
                gender = "F"
            )
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        PrivacyOptionsScreen(
            state = previewState,
            paddingFromSystemUi = PaddingValues(0.dp),
            isPlayerSelected = { false },
            eventCallback = {}
        )
    }
}
