package cy.volleybolley.profile.presentation.ui.screens.players

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.PlayerProfileRoute
import cy.volleybolley.core.util.VolleyLog
import cy.volleybolley.core.util.createDebounceMethod
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.usecase.GetPlayersInteractor
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEffect.NavigateFromPlayersScreen
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnAllPlayers
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnBackFromPlayers
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnFavoritePlayers
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnListItem
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.ClickOnSearchButton
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenEvent.SearchTextChanged
import cy.volleybolley.profile.presentation.ui.screens.players.model.BackPlayerIdHolder
import kotlinx.coroutines.flow.update

class PlayersScreenViewModel(
    private val backPlayerIdHolder: BackPlayerIdHolder,
    private val interactor: GetPlayersInteractor,
) : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect>(
    initialState = PlayersScreenState()
) {
    init {
        updatePlayers()
    }

    private val searchWithDebounce: (String) -> Unit = createDebounceMethod(
        delayMillis = SEARCH_DELAY,
        coroutineScope = viewModelScope,
        restartActionOnLastParam = true
    ) { text -> searchByText(text) }

    override fun obtainEvent(event: PlayersScreenEvent) {
        when (event) {
            ClickOnBackFromPlayers -> sendUiEffect(NavigateFromPlayersScreen(null))

            is SearchTextChanged -> {
                uiStateMutable.update { it.copy(searchText = event.text) }
                searchWithDebounce(event.text)
            }

            is ClickOnSearchButton -> searchByText(event.text)

            ClickOnAllPlayers -> clickOnModeSwitchButton(isClickOnAllPlayers = true)

            ClickOnFavoritePlayers -> clickOnModeSwitchButton(isClickOnAllPlayers = false)

            is ClickOnListItem -> sendUiEffect(
                NavigateFromPlayersScreen(
                    PlayerProfileRoute(event.playerId)
                )
            )
        }
    }

    private fun updatePlayers() {
        launchSafe(
            getErrorLogMessage = {
                "PlayersScreen >>> updatePlayers() >>> error: ${it.message}"
            },
            onError = {
                sendUiEffect(PlayersScreenEffect.ShowToast("Update players failed with error: ${it.message}"))
            }
        ) {
            if (!uiState.value.isLoading) uiStateMutable.update { it.copy(isLoading = true) }

            interactor.updatePlayers(showAllPlayers = uiState.value.showAllPlayers)
                .onSuccess { players ->
                    uiStateMutable.update { it.copy(players = players, isLoading = false) }
                }
                .onFailure { errorType ->
                    VolleyLog.e(tag, "PlayersScreen >>> updatePlayers(): $errorType")
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(PlayersScreenEffect.ShowToast("Update players failed!"))
                }
        }
    }

    private fun clickOnModeSwitchButton(isClickOnAllPlayers: Boolean) {
        val condition: Boolean
        val playersListToUpdate: List<Player>
        val showAllStatus: Boolean
        when (isClickOnAllPlayers) {
            true -> {
                condition = !uiState.value.showAllPlayers
                playersListToUpdate = interactor.fetchCachedPlayers()
                showAllStatus = true
            }

            else -> {
                condition = uiState.value.showAllPlayers
                playersListToUpdate = interactor.fetchCachedFavoritePlayers()
                showAllStatus = false
            }
        }

        if (condition) {
            uiStateMutable.update {
                it.copy(
                    searchText = "",
                    showAllPlayers = showAllStatus,
                    players = playersListToUpdate
                )
            }
        }
    }

    private fun searchByText(text: String) {
        uiStateMutable.update { it.copy(isLoading = true) }
        uiStateMutable.update {
            if (it.showAllPlayers) {
                it.copy(
                    players = interactor.fetchCachedPlayers().filter { player ->
                        isPlayerExistByText(player, text)
                    },
                    isLoading = false
                )
            } else {
                it.copy(
                    players = interactor.fetchCachedFavoritePlayers().filter { player ->
                        isPlayerExistByText(player, text)
                    },
                    isLoading = false
                )
            }
        }
    }

    private fun isPlayerExistByText(player: Player, text: String): Boolean {
        val correctText = text.lowercase()
        val fullName = "${player.firstName} ${player.lastName}".lowercase()
        val textChunks = correctText.split(" ", ignoreCase = true)
        return when {
            fullName.contains(correctText) -> true
            checkNameByChunks(player.firstName.lowercase(), player.lastName.lowercase(), textChunks) -> true
            else -> false
        }
    }

    private fun checkNameByChunks(firstName: String, lastName: String, chunks: List<String>): Boolean {
        return if (chunks.size < 2) {
            false
        } else {
            firstName.contains(chunks[0]) || lastName.contains(chunks[1])
        }
    }

    fun handleBackPlayerId() {
//        backPlayerIdHolder.getPlayerId()?.let { idForChangeFavoriteStatus ->
//            val changesIndex = originAllPlayers.indexOfFirst { it.id == idForChangeFavoriteStatus }
//            if (changesIndex != -1) {
//                val newFavoriteStatus = !originAllPlayers[changesIndex].isFavorite
//                originAllPlayers[changesIndex] = originAllPlayers[changesIndex].copy(isFavorite = newFavoriteStatus)
//                updateFavoritePlayers()
//            }
//            backPlayerIdHolder.clearBackPlayerId()
//        }
    }

    companion object {
        const val SEARCH_DELAY = 1500L
    }
}
