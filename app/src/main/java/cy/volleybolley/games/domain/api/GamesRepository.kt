package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.Preview
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame
import cy.volleybolley.games.domain.model.event.game.GameDetails
import cy.volleybolley.games.domain.model.event.game.JoinedGame

interface GamesRepository {
    suspend fun createGame(game: CreateGame): VolleyResult<CreatedGame, ErrorType>
    suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType>
    suspend fun invitePlayersToGame(gameId: Int, players: List<PlayerShort>): VolleyResult<Unit, ErrorType>
    suspend fun getPreview(): VolleyResult<Preview, ErrorType>
    suspend fun getMyGames(): VolleyResult<List<Event>, ErrorType>
    suspend fun getInvites(): VolleyResult<List<Event>, ErrorType>
    suspend fun getArchive(): VolleyResult<List<Event>, ErrorType>
    suspend fun getUpcoming(): VolleyResult<List<Event>, ErrorType>
    suspend fun joinGame(gameId: Int): VolleyResult<JoinedGame, ErrorType>
    suspend fun declineGameInvite(gameId: Int): VolleyResult<Unit, ErrorType>
    suspend fun cancelGame(gameId: Int): VolleyResult<Unit, ErrorType>
    suspend fun getPlayersToRate(gameId: Int): VolleyResult<List<PlayerShort>, ErrorType>
    suspend fun ratePlayers(gameId: Int, players: List<RatePlayer>): VolleyResult<Unit, ErrorType>
    suspend fun skipRating(gameId: Int): VolleyResult<Unit, ErrorType>
}
