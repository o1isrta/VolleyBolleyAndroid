package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createNewGame.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createNewGame.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createNewGame.model.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class TourneyEnteringConditionsScreenViewModel(
    private val gameRepository: CreateNewGameRepository
) : BaseViewModel<
    TourneyEnteringConditionsScreenState,
    TourneyEnteringConditionsScreenEvent,
    TourneyEnteringConditionsScreenEffect
    >(
    TourneyEnteringConditionsScreenState()
) {
    init {
        viewModelScope.launch {
            gameRepository.gameData.collectLatest { gameDataFromRepo ->
                uiStateMutable.update { currentState ->
                    currentState.copy(
                        maximumPlayers = gameDataFromRepo.maximumPlayers,
                        perPerson = gameDataFromRepo.perPerson,
                        accountNumber = gameDataFromRepo.accountNumber,
                        isPrivate = gameDataFromRepo.players.isNotEmpty()
                    )
                }
            }
        }
    }

    override fun obtainEvent(event: TourneyEnteringConditionsScreenEvent) {
        when (event) {
            is TourneyEnteringConditionsScreenEvent.OnPublicSelected -> onPublicSelected()
            is TourneyEnteringConditionsScreenEvent.OnPrivateSelected -> onPrivateSelected()
            is TourneyEnteringConditionsScreenEvent.OnSaveTourneyClick -> saveTourney()
            is TourneyEnteringConditionsScreenEvent.PerPersonChanged -> {
                uiStateMutable.update { it.copy(perPerson = event.perPerson) }
            }
            is TourneyEnteringConditionsScreenEvent.MaximumPlayersChanged -> {
                uiStateMutable.update { it.copy(maximumPlayers = event.maximumPlayers) }
            }
            is TourneyEnteringConditionsScreenEvent.OnBackClicked -> {
                sendUiEffect(TourneyEnteringConditionsScreenEffect.NavigateBack)
            }
            is TourneyEnteringConditionsScreenEvent.OnAddPaymentClick -> {
                sendUiEffect(TourneyEnteringConditionsScreenEffect.NavigateToPayments)
            }
        }
    }

    private fun onPublicSelected() {
        uiStateMutable.update { it.copy(isPrivate = false) }
    }

    private fun onPrivateSelected() {
        uiStateMutable.update { it.copy(isPrivate = true) }
    }

    private fun saveTourney() {
        uiStateMutable.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error saving tourney: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                uiStateMutable.update {
                    it.copy(isLoading = false, errorMessage = er.message ?: "Failed to save tourney")
                }
                sendUiEffect(
                    TourneyEnteringConditionsScreenEffect.ShowErrorMessage(er.message ?: "Failed to save tourney")
                )
            }
        ) {
            gameRepository.updateGameData { gameData ->
                gameData.copy(
                    maximumPlayers = uiState.value.maximumPlayers,
                    accountNumber = uiState.value.accountNumber,
                    perPerson = uiState.value.perPerson
                )
            }
            gameRepository.saveGameDataToServer()
            uiStateMutable.update { it.copy(isLoading = false) }
            sendUiEffect(TourneyEnteringConditionsScreenEffect.NavigateToSuccess)
        }
    }
}

class TourneyEnteringConditionsScreenViewModelPreview : TourneyEnteringConditionsScreenViewModel(
    FakeCreateNewGameRepository(GameData())
)
