package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface PlayerProfileScreenEffect : UiEffect {
    data class NavigateFromPlayerDetailScreen(val playerIdWithChangedFavoriteStatus: Int?) : PlayerProfileScreenEffect
}
