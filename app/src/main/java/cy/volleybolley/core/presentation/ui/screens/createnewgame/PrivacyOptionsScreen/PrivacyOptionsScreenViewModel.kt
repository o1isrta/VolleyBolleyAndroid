package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.FakeSearchPlayersUseCase
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.GameData
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.usecase.SearchPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.E

open class PrivacyOptionsScreenViewModel(private val gameRepository: CreateNewGameRepository,
                                         private val searchPlayersUseCase: SearchPlayersUseCase) : BaseViewModel<PrivacyOptionsScreenState, PrivacyOptionsScreenEvent, PrivacyOptionsScreenEffect>(
    PrivacyOptionsScreenState()
) {
    override val tag: String = "PrivacyOptionsScreenViewModel"

    init {
        // Загружаем игроков при инициализации ViewModel
        obtainEvent(PrivacyOptionsScreenEvent.LoadPlayers)
    }

    override fun obtainEvent(event: PrivacyOptionsScreenEvent) {
        when (event) {
            is PrivacyOptionsScreenEvent.OnBackClicked -> {
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBack)
            }
            is PrivacyOptionsScreenEvent.OnAddSelectedClick -> {
                onAddSelectedPlayersClick()
            }
            is PrivacyOptionsScreenEvent.OnPlayerSelectionChange -> {
                onPlayerSelectionChange(event.player, event.isSelected)
            }
            is PrivacyOptionsScreenEvent.OnQueryChanged -> {
                onQueryChange(event.text)
            }
            is PrivacyOptionsScreenEvent.AllOrFavoritesSelected -> {

            }
            PrivacyOptionsScreenEvent.LoadPlayers -> searchPlayers(uiState.value.searchQuery)
        }
    }

    private fun searchPlayers(query: String) {
        launchSafe(
            onError = { throwable ->
                sendUiEffect(PrivacyOptionsScreenEffect.ShowError(throwable.localizedMessage ?: "Unknown error"))
                uiStateMutable.update { it.copy(isLoading = false, playersSearchResult = emptyList()) } // Сбросить загрузку и очистить/обновить игроков
            },
            getErrorLogMessage = { "Error searching players for query: $query - $it" }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }
            // Вот здесь изменение:
            when (val result = searchPlayersUseCase(query)) {
                is VolleyResult.Success -> {
                    uiStateMutable.update { it.copy(playersSearchResult = result.data, isLoading = false) }
                }
                is VolleyResult.Failure -> {
                    val errorMessage = when (result.error) {
                        ErrorType.UNAUTHORIZED -> "Authentication required"
                        ErrorType.NO_CONNECTION -> "Network unavailable, please check your connection."
                        ErrorType.SERVER_ERROR -> "Server is busy, please try again later."
                        ErrorType.NOT_FOUND -> "No players found."
                        ErrorType.UNKNOWN_ERROR -> "An unexpected error occurred."
                        ErrorType.BAD_REQUEST -> "Bad request"// Добавьте другие ErrorType по мере необходимости
                    }
                    sendUiEffect(PrivacyOptionsScreenEffect.ShowError(errorMessage))
                    uiStateMutable.update { it.copy(isLoading = false, playersSearchResult = emptyList()) } // Сбросить или очистить игроков при ошибке
                }
            }
        }
    }

    private fun onQueryChange(query: String) {
        uiStateMutable.update { it.copy(query = query) }
        searchPlayers(query)
    }

    private fun onPlayerSelectionChange(player: Player, isSelected: Boolean) {
        uiStateMutable.update { currentState ->
            val updatedSelectedPlayers = currentState.selectedPlayers.toMutableSet()
            if (isSelected) {
                updatedSelectedPlayers.add(player)
            } else {
                updatedSelectedPlayers.remove(player)
            }
            currentState.copy(selectedPlayers = updatedSelectedPlayers)
        }
    }

    private fun onAddSelectedPlayersClick() {
        launchSafe(
            onError = { throwable ->
                sendUiEffect(PrivacyOptionsScreenEffect.ShowError(throwable.localizedMessage ?: "Unknown error while adding players"))
                uiStateMutable.update { it.copy(isLoading = false) }
            },
            getErrorLogMessage = { "Error adding selected players: $it" }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }
            updateGameDataUseCase { gameData ->
                gameData.copy(selectedPlayers = uiState.value.selectedPlayers.toList())
            }.onSuccess {
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBackWithSelectedPlayers)
            }.onFailure { error ->
                sendUiEffect(PrivacyOptionsScreenEffect.ShowError(error.localizedMessage ?: "Failed to add selected players"))
                uiStateMutable.update { it.copy(isLoading = false) }
            }
        }
    }
}

// Специальный ViewModel для Preview
class PrivacyOptionsScreenViewModelPreview : PrivacyOptionsScreenViewModel(
    FakeCreateNewGameRepository(MutableStateFlow(GameData())), FakeSearchPlayersUseCase()
) {
    init {
        // Например, чтобы показать, что что-то загружается
        // _state.value = _state.value.copy(isLoading = true)
    }
}
