package cy.volleybolley.games.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.dto.mappers.toPlayersData
import cy.volleybolley.games.data.network.TournamentsRequest
import cy.volleybolley.games.data.network.TournamentsResponse
import cy.volleybolley.games.domain.api.TournamentParticipationRepository
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.tournament.JoinedTournament

class TournamentParticipationRepositoryImpl(
    private val networkClient: NetworkClient<TournamentsRequest, TournamentsResponse>
) : TournamentParticipationRepository {
    override suspend fun invitePlayersToTournament(
        tournamentId: Int,
        players: List<PlayerShort>
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            TournamentsRequest.InvitePlayersToTournament(
                tournamentId = tournamentId,
                players = players.toPlayersData()
            )
        )

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun joinTournament(tournamentId: Int, teamId: Int?): VolleyResult<JoinedTournament, ErrorType> {
        val response =
            networkClient.getResponse(TournamentsRequest.JoinTournament(tournamentId = tournamentId, teamId = teamId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val tournament = (response.body as? TournamentsResponse.JoinTournament)?.toDomain()
        return tournament?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun declineTournamentInvite(tournamentId: Int): VolleyResult<Unit, ErrorType> {
        val response =
            networkClient.getResponse(TournamentsRequest.DeclineTournamentInvite(tournamentId = tournamentId))

        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
