package cy.volleybolley.games.domain.model.event

import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host

data class Event(
    val id: Int,
    val type: EventType,
    val host: Host,
    val location: Location,
    val message: String,
    val startTime: String,
    val endTime: String,
)
