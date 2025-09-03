package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface PlayerProfileScreenEvent : UiEvent {
    data object ClickOnBackFromPlayerDetails : PlayerProfileScreenEvent
    data object ClickOnFavoriteManagementButton : PlayerProfileScreenEvent
}
