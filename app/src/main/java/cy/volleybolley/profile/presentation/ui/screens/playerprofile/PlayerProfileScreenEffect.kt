package cy.volleybolley.profile.presentation.ui.screens.playerprofile

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface PlayerProfileScreenEffect : UiEffect {
    data class NavigateFromPlayerDetailScreen(val playerIdWithChangedFavoriteStatus: Int?) : PlayerProfileScreenEffect
}
