package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toData
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.network.TournamentsRequest
import cy.volleybolley.games.data.network.TournamentsResponse
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TournamentRatingRepositoryImpl(
    private val networkClient: NetworkClient<TournamentsRequest, TournamentsResponse>,
    private val applicationScope: CoroutineScope
) : TournamentRatingRepository {
    override suspend fun getPlayersToRate(tournamentId: Int): VolleyResult<List<PlayerShort>, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.GetPlayersToRate(tournamentId = tournamentId))

        if (!response.isSuccess) return VolleyResult.Failure(response.resultCode.mapToErrorType())

        val preview = (response.body as? TournamentsResponse.GetPlayersToRate)?.players?.toDomain()
        return preview?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override fun ratePlayers(
        tournamentId: Int,
        players: List<RatePlayer>,
        onResult: (VolleyResult<Unit, ErrorType>) -> Unit
    ) {
        applicationScope.launch {
            val response = networkClient.getResponse(
                TournamentsRequest.RatePlayers(
                    tournamentId = tournamentId,
                    players = players.toData()
                )
            )
            val result: VolleyResult<Unit, ErrorType> = if (response.isSuccess) {
                VolleyResult.Success(Unit)
            } else {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }

            onResult(result)
        }
    }


    override suspend fun skipRating(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(TournamentsRequest.SkipRating(tournamentId = tournamentId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
