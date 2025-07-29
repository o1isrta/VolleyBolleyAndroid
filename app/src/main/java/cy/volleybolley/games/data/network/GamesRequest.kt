package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.Game

sealed interface GamesRequest {
    class CreateGameRequest(
        val path: String = "/games",
        val game: Game,
    ) : GamesRequest
}
