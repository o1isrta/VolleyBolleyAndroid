package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.Game
import kotlinx.serialization.Serializable

sealed interface GamesResponse {
    @Serializable
    class CreateGameResponse(
        val game: Game
    ): GamesResponse
}
