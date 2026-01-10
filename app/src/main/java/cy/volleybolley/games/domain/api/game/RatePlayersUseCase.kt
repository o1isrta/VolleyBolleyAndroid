package cy.volleybolley.games.domain.api.game

import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.event.EventType

interface RatePlayersUseCase {
    fun ratePlayers(
        id: Int,
        type: EventType,
        players: List<RatePlayer>,
    )
}
