package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.PlayerProfileRoute
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEffect.NavigateFromPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnAllPlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnBackFromPlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnFavoritePlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnListItem
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnSearchButton
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.SearchTextChanged
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.BackPlayerIdHolder
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.PlayerTemp
import kotlinx.coroutines.flow.update

class PlayersScreenViewModel(
    private val backPlayerIdHolder: BackPlayerIdHolder,
) : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect> (
    initialState = PlayersScreenState()
) {
    private val originAllPlayers: MutableList<PlayerTemp> = mutableListOf()
    private val originFavoritePlayers: MutableList<PlayerTemp> = mutableListOf()

    init {
        // getPlayers()
        originAllPlayers.addAll(VolleyUiUtil.mockPlayers)
        originFavoritePlayers.addAll(getFavoritePlayers())
        _uiState.update { it.copy(players = originAllPlayers) }
    }

    override val tag = TAG

    override fun obtainEvent(event: PlayersScreenEvent) {
        when(event) {
            ClickOnBackFromPlayers -> sendUiEffect(NavigateFromPlayersScreen(null))

            is SearchTextChanged -> {
                _uiState.update { it.copy(searchText = event.text) }
            }

            is ClickOnSearchButton -> {
                _uiState.update {
                    if (it.showAllPlayers) {
                        it.copy(
                            players = originAllPlayers.filter { player ->
                                isPlayerExistByText(player, event.text)
                            }
                        )
                    } else {
                        it.copy(
                            players = originFavoritePlayers.filter { player ->
                                isPlayerExistByText(player, event.text)
                            }
                        )
                    }
                }
            }

            ClickOnAllPlayers -> {
                if (!uiState.value.showAllPlayers) {
                    _uiState.update {
                        it.copy(
                            searchText = "",
                            showAllPlayers = true,
                            players = originAllPlayers
                        )
                    }
                }
            }

            ClickOnFavoritePlayers -> {
                if (uiState.value.showAllPlayers) {
                    _uiState.update {
                        it.copy(
                            searchText = "",
                            showAllPlayers = false,
                            players = originFavoritePlayers
                        )
                    }
                }
            }

            is ClickOnListItem -> sendUiEffect(NavigateFromPlayersScreen(PlayerProfileRoute(event.playerId)))
        }
    }

    private fun getFavoritePlayers(): List<PlayerTemp> =
        originAllPlayers.filter { it.isFavorite }

    private fun updateFavoritePlayers() {
        originFavoritePlayers.clear()
        originFavoritePlayers.addAll(getFavoritePlayers())
    }

    private fun isPlayerExistByText(player: PlayerTemp, text: String): Boolean {
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
        backPlayerIdHolder.getPlayerId()?.let { idForChangeFavoriteStatus ->
            val changesIndex = originAllPlayers.indexOfFirst { it.id == idForChangeFavoriteStatus }
            if (changesIndex != -1) {
                val newFavoriteStatus = !originAllPlayers[changesIndex].isFavorite
                originAllPlayers[changesIndex] = originAllPlayers[changesIndex].copy(isFavorite = newFavoriteStatus)
                updateFavoritePlayers()
            }
            backPlayerIdHolder.clearBackPlayerId()
        }
    }

    companion object {
        val TAG = PlayersScreenViewModel::class.simpleName ?: "PlayersScreenViewModel"
    }
}
