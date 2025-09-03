package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.PlayerProfileRoute
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEffect.NavigateFromPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnAllPlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnBackFromPlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnFavoritePlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnListItem
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnSearchButton
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.SearchTextChanged
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.BackPlayerHolder
import cy.volleybolley.core.presentation.ui.screens.profile.players.model.PlayerTemp
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class PlayersScreenViewModel(
    private val backPlayerHolder: BackPlayerHolder,
    private val json: Json,
) : BaseViewModel<PlayersScreenState, PlayersScreenEvent, PlayersScreenEffect> (
    initialState = PlayersScreenState()
) {
    private val originPlayers: List<PlayerTemp>
    val mockPlayers: List<PlayerTemp> = listOf(
        PlayerTemp(id = 1, firstName = "Иван", lastName = "Иванов", avatarUrl = null, isFavorite = true, level = "LIGHT"),
        PlayerTemp(id = 2, firstName = "Анна", lastName = "Петрова", avatarUrl = null, isFavorite = false, level = "MEDIUM"),
        PlayerTemp(id = 3, firstName = "Сергей", lastName = "Смирнов", avatarUrl = null, isFavorite = true, level = "HARD"),
        PlayerTemp(id = 4, firstName = "Елена", lastName = "Васильева", avatarUrl = null, isFavorite = false, level = "PRO"),
        PlayerTemp(id = 5, firstName = "Дмитрий", lastName = "Попов", avatarUrl = null, isFavorite = false, level = "LIGHT"),
        PlayerTemp(id = 6, firstName = "Ольга", lastName = "Кузнецова", avatarUrl = null, isFavorite = true, level = "MEDIUM"),
        PlayerTemp(id = 7, firstName = "Петр", lastName = "Новиков", avatarUrl = null, isFavorite = false, level = "HARD"),
        PlayerTemp(id = 8, firstName = "Марина", lastName = "Иванова", avatarUrl = null, isFavorite = false, level = "PRO"),
        PlayerTemp(id = 9, firstName = "Максим", lastName = "Семенов", avatarUrl = null, isFavorite = false, level = "LIGHT"),
        PlayerTemp(id = 10, firstName = "Светлана", lastName = "Дмитриева", avatarUrl = null, isFavorite = false, level = "MEDIUM"),
    )

    init {
        // getPlayers()
        originPlayers = mockPlayers
        _uiState.update { it.copy(players = originPlayers) }
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
                    it.copy(
                        players = it.players.filter { player ->
                            filterPlayerByText(player, event.text)
                        }
                    )
                }
            }

            ClickOnAllPlayers -> {
                if (!uiState.value.showAllPlayers) {
                    _uiState.update {
                        it.copy(
                            showAllPlayers = true,
                            players = originPlayers
                        )
                    }
                }
            }

            ClickOnFavoritePlayers -> {
                if (uiState.value.showAllPlayers) {
                    _uiState.update {
                        it.copy(
                            showAllPlayers = false,
                            players = originPlayers.filter { it.isFavorite }
                        )
                    }
                }
            }

            is ClickOnListItem -> sendUiEffect(NavigateFromPlayersScreen(PlayerProfileRoute(event.playerId)))
        }

    }

    private fun filterPlayerByText(player: PlayerTemp, text: String): Boolean {
        val fullName = "${player.firstName} ${player.lastName}"
        val textChunks = text.split(" ")
        return when {
            fullName.contains(text) -> true
            checkNameByChunks(player.firstName, player.lastName, textChunks) -> true
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

    companion object {
        val TAG = PlayersScreenViewModel::class.simpleName ?: "PlayersScreenViewModel"
    }
}
