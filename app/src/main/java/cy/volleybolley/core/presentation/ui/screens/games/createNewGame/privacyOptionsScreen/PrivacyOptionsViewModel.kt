package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.privacyOptionsScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.R
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.CreateGameSharedViewModel
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.usecase.SearchPlayersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class PrivacyOptionsViewModel(
    private val createGameSharedViewModel: CreateGameSharedViewModel,
    private val searchPlayersUseCase: SearchPlayersUseCase
) : BaseViewModel<PrivacyOptionsState, PrivacyOptionsEvent, PrivacyOptionsEffect>(
    PrivacyOptionsState()
) {
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            createGameSharedViewModel.gameData
                .collectLatest { gameData ->
                    uiStateMutable.update { currentState ->
                        val selectedPlayers = gameData.players.toSet() //?: emptySet() // gameDataFromRepo.players.toSet()
                        currentState.copy(
                            flagFavorites = false,
                            playersSearchResult = emptyList(),
                            selectedPlayers = selectedPlayers,
                            filteredPlayers = filterPlayers(
                                selectedPlayers,
                                emptyList(),
                                false
                            )
                        )
                    }
                }
        }
    }

    private fun updateFilteredPlayers() {
        uiStateMutable.update { currentState ->
            currentState.copy(
                filteredPlayers = filterPlayers(
                    currentState.selectedPlayers,
                    currentState.playersSearchResult,
                    currentState.flagFavorites
                )
            )
        }
    }

    private fun filterPlayers(
        selectedPlayers: Set<Player>,
        searchResult: List<Player>,
        flagFavorites: Boolean
    ): List<Player> {
        val allPlayers = (selectedPlayers union searchResult).toList()
        return if (flagFavorites) {
            allPlayers.filter { it.isFavorite }
        } else {
            allPlayers
        }
    }

    override fun obtainEvent(event: PrivacyOptionsEvent) {
        when (event) {
            is PrivacyOptionsEvent.OnBackClicked -> {
                sendUiEffect(PrivacyOptionsEffect.NavigateBack)
            }

            is PrivacyOptionsEvent.OnAddSelectedClick -> {
                onAddSelectedPlayersClick()
                sendUiEffect(PrivacyOptionsEffect.NavigateBack)
            }

            is PrivacyOptionsEvent.OnPlayerSelectionClick -> {
                onPlayerSelectionChange(event.player)
            }

            is PrivacyOptionsEvent.OnQueryChanged -> {
                onQueryChange(event.text)
            }

            is PrivacyOptionsEvent.AllOrFavoritesSelected -> {
                uiStateMutable.update { it.copy(flagFavorites = event.isFavorites) }
                updateFilteredPlayers()
            }
        }
    }

    private fun onQueryChange(queryText: String) {
        // Отменяем предыдущий Job, если он существует
        searchJob?.cancel()
        // Запускаем новый Job с задержкой
        searchJob = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                uiStateMutable.value = uiStateMutable.value.copy(query = queryText)
            }
            delay(DEBOUNCE_DELAY_300MS)
            searchPlayers(uiStateMutable.value.query)
        }
    }

    private fun searchPlayers(query: String) {
        launchSafe(
            onError = { throwable ->
                handleSearchError(throwable /*, query*/)
            },
            getErrorLogMessage = { "Error searching players for query: $query - $it" }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }
            delay(DEBOUNCE_DELAY_500MS) // Имитируем задержку сети

            val result = searchPlayersUseCase(query)
            processSearchResult(result, query)
        }
    }

    private fun handleSearchError(throwable: Throwable) {
        sendUiEffect(PrivacyOptionsEffect.ShowErrorMessage(throwable.localizedMessage ?: "Unknown error"))
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                playersSearchResult = emptyList()
            )
        }
    }

    private fun processSearchResult(result: VolleyResult<List<Player>, ErrorType>, query: String) {
        when (result) {
            is VolleyResult.Success -> handleSuccess(result.data, query)
            is VolleyResult.Failure -> handleFailure(result.error)
        }
    }

    private fun handleSuccess(players: List<Player>, query: String) {
        val filteredByQuery = if (query.isBlank()) {
            players
        } else {
            players.filter { player ->
                player.firstName.contains(query, ignoreCase = true) ||
                    player.lastName.contains(query, ignoreCase = true)
            }
        }

        uiStateMutable.update {
            it.copy(
                playersSearchResult = filteredByQuery,
                isLoading = false
            )
        }
        updateFilteredPlayers()
    }

    private fun handleFailure(error: ErrorType) {
        val errorMessageId = when (error) {
            ErrorType.UNAUTHORIZED -> R.string.authentication_required
            ErrorType.NO_CONNECTION -> R.string.network_unavailable
            ErrorType.SERVER_ERROR -> R.string.server_is_busy
            ErrorType.NOT_FOUND -> R.string.no_players_found
            ErrorType.UNKNOWN_ERROR -> R.string.an_unexpected_error_occurred
            ErrorType.BAD_REQUEST -> R.string.bad_request
            ErrorType.NO_REFRESH_TOKEN -> R.string.no_refresh_token
        }

        sendUiEffect(PrivacyOptionsEffect.ShowErrorMessageById(errorMessageId))
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                playersSearchResult = emptyList()
            )
        }
    }

    private fun onPlayerSelectionChange(player: Player) {
        uiStateMutable.update { currentState ->
            val updatedSelectedPlayers = if (isPlayerSelected(player)) {
                currentState.selectedPlayers - player
            } else {
                val maximumPlayers = createGameSharedViewModel.gameData.value.maximumPlayers
                if (currentState.selectedPlayers.size < maximumPlayers) {
                    currentState.selectedPlayers + player
                } else {
                    currentState.selectedPlayers
                }
            }
            currentState.copy(selectedPlayers = updatedSelectedPlayers)
        }
        updateFilteredPlayers()
    }

    private fun onAddSelectedPlayersClick() {
        viewModelScope.launch {
            createGameSharedViewModel.updateGameData { currentData ->
                currentData.copy(players = uiState.value.selectedPlayers.toList())
            }
        }.invokeOnCompletion { // вызывается, когда корутина завершилась
            clearSearchResults() // Очищаем результаты поиска после завершения
        }
    }

    // Функция для очистки списка результатов поиска
    private fun clearSearchResults() {
        uiStateMutable.update { currentState ->
            currentState.copy(playersSearchResult = emptyList())
        }
    }

    fun isPlayerSelected(player: Player): Boolean {
        return uiStateMutable.value.selectedPlayers.contains(player)
    }

    private companion object {
        const val DEBOUNCE_DELAY_300MS = 300L
        const val DEBOUNCE_DELAY_500MS = 500L
    }
}
