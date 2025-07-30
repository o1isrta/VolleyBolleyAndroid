package cy.volleybolley.games.data.network

import cy.volleybolley.games.data.dto.GameDto

sealed interface GamesRequest {
    class CreateGame(
        val path: String = "/games",
        val game: GameDto,
    ) : GamesRequest

    class GetGameDetails(): GamesRequest
}
