package cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.players.domain.model.Player

sealed interface PrivacyOptionsScreenEvent : UiEvent {
    data object OnAddSelectedClick : PrivacyOptionsScreenEvent
    data object OnBackClicked : PrivacyOptionsScreenEvent
    data class OnQueryChanged(val text: String) : PrivacyOptionsScreenEvent
    data class OnPlayerSelectionClick(val player: Player) : PrivacyOptionsScreenEvent
    data class AllOrFavoritesSelected(val isFavorites: Boolean) : PrivacyOptionsScreenEvent
}
