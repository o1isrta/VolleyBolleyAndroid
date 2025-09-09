package cy.volleybolley.profile.presentation.ui.screens.players

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.PlayerProfileRoute
import cy.volleybolley.profile.presentation.ui.screens.players.model.BackPlayerIdHolder
import cy.volleybolley.profile.presentation.ui.screens.players.model.PlayerTemp
import kotlinx.coroutines.flow.update

class PlayersScreenViewModel(
    private val backPlayerIdHolder: BackPlayerIdHolder,
) : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect>(
    initialState = PlayersScreenState()
) {
    private val originAllPlayers: MutableList<PlayerTemp> = mutableListOf()
    private val originFavoritePlayers: MutableList<PlayerTemp> = mutableListOf()

    init {
        // getPlayers()
        originAllPlayers.addAll(VolleyUiUtil.mockPlayers)
        originFavoritePlayers.addAll(getFavoritePlayers())
        uiStateMutable.update { it.copy(players = originAllPlayers) }
    }

    override val tag = TAG

    override fun obtainEvent(event: PlayersScreenEvent) {
        when (event) {
            PlayersScreenEvent.ClickOnBackFromPlayers -> sendUiEffect(PlayersScreenEffect.NavigateFromPlayersScreen(null))

            is PlayersScreenEvent.SearchTextChanged -> {
                uiStateMutable.update { it.copy(searchText = event.text) }
            }

            is PlayersScreenEvent.ClickOnSearchButton -> {
                uiStateMutable.update {
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

            PlayersScreenEvent.ClickOnAllPlayers -> {
                clickOnModeSwitchButton(isClickOnAllPlayers = true)
            }

            PlayersScreenEvent.ClickOnFavoritePlayers -> {
                clickOnModeSwitchButton(isClickOnAllPlayers = false)
            }

            is PlayersScreenEvent.ClickOnListItem -> sendUiEffect(
                PlayersScreenEffect.NavigateFromPlayersScreen(
                    PlayerProfileRoute(event.playerId)
                )
            )
        }
    }

    private fun clickOnModeSwitchButton(isClickOnAllPlayers: Boolean) {
        val condition: Boolean
        val playersListToUpdate: List<PlayerTemp>
        val showAllStatus: Boolean
        when (isClickOnAllPlayers) {
            true -> {
                condition = !uiState.value.showAllPlayers
                playersListToUpdate = originAllPlayers
                showAllStatus = true
            }

            else -> {
                condition = uiState.value.showAllPlayers
                playersListToUpdate = originFavoritePlayers
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
