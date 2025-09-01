package cy.volleybolley.core.presentation.ui.screens.profile.players.model

import cy.volleybolley.courts.domain.model.Location

data class PlayerActivityTemp(
    val eventTimestamp: String,
    val courtLocation: Location
)
