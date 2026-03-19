package cy.volleybolley.games.domain.model.event

import kotlinx.serialization.Serializable

@Serializable
enum class EventType {
    @Serializable
    GAME,
    @Serializable
    TOURNAMENT
}
