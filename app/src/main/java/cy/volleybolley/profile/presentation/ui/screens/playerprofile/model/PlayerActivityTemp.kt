package cy.volleybolley.profile.presentation.ui.screens.playerprofile.model

import cy.volleybolley.courts.domain.model.Location

data class PlayerActivityTemp(
    val eventTimestamp: String,
    val courtLocation: Location
)
