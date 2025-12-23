package cy.volleybolley.profile.presentation.ui.screens.playerprofile

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerDetailTemp

data class PlayerProfileScreenState(
    val playerDetail: PlayerDetailTemp
) : UiState
