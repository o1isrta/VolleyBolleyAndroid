package cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.players.domain.model.Player

sealed interface PrivacyOptionsEvent : UiEvent {
    data object OnAddSelectedClick : PrivacyOptionsEvent
    data object OnBackClicked : PrivacyOptionsEvent
    data class OnQueryChanged(val text: String) : PrivacyOptionsEvent
    data class OnPlayerSelectionClick(val player: Player) : PrivacyOptionsEvent
    data class AllOrFavoritesSelected(val isFavorites: Boolean) : PrivacyOptionsEvent
}
