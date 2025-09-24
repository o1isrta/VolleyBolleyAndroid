package cy.volleybolley.players.domain.model

import cy.volleybolley.courts.domain.model.Location

data class PlayerActivity(
    val eventTimestamp: String,
    val courtLocation: Location
)
