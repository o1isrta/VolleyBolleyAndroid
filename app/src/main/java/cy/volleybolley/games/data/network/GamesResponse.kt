package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.GameDto
import kotlinx.serialization.Serializable

sealed interface GamesResponse {
    @Serializable
    class CreateGame(
        val game: GameDto
    ): GamesResponse

    class GetGameDetail(): GamesResponse
}
