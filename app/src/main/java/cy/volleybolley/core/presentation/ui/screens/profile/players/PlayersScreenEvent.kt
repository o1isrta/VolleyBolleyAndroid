package cy.volleybolley.core.presentation.ui.screens.profile.players

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface PlayersScreenEvent : UiEvent {
    data object ClickOnBackFromPlayers : PlayersScreenEvent
    data class SearchTextChanged(val text: String) : PlayersScreenEvent
    data class ClickOnSearchButton(val text: String) : PlayersScreenEvent
    data object ClickOnAllPlayers : PlayersScreenEvent
    data object ClickOnFavoritePlayers : PlayersScreenEvent
    data class ClickOnListItem(val playerId: String) : PlayersScreenEvent
}
