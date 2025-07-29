package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.toData
import cy.volleybolley.games.data.dto.toDomain
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.model.Game

class GamesRepositoryImpl(
    val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GamesRepository {
    override suspend fun createGame(game: Game): VolleyResult<Game?, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.CreateGameRequest(game = game.toData()))

        return when (response.isSuccess) {
            true -> {
                VolleyResult.Success(
                    (response.body as? GamesResponse.CreateGameResponse)?.game?.toDomain()
                )
            }

            false -> {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }
        }
    }
}
