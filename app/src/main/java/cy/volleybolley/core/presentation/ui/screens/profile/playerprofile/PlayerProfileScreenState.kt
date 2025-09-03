package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.model.PlayerDetailTemp

data class PlayerProfileScreenState(
    val playerDetail: PlayerDetailTemp
) : UiState
