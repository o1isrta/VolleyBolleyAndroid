package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.dto.mappers.toPlayersData
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.api.GameParticipationRepository
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.game.JoinedGame

class GameParticipationRepositoryImpl(
    private val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GameParticipationRepository {
    override suspend fun invitePlayersToGame(gameId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            GamesRequest.InvitePlayersToGame(
                gameId = gameId,
                players = players.toPlayersData()
            )
        )

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun joinGame(gameId: Int): VolleyResult<JoinedGame, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.JoinGame(gameId = gameId))

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val game = response.body as? GamesResponse.JoinGame

        return game?.let {
            VolleyResult.Success(data = game.toDomain())
        } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.DeclineGameInvite(gameId = gameId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
